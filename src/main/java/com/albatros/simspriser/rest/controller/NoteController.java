package com.albatros.simspriser.rest.controller;

import com.albatros.simspriser.domain.DiraNote;
import com.albatros.simspriser.domain.Schedule;
import com.albatros.simspriser.service.NoteService;
import com.albatros.simspriser.service.ScheduleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("/note")
@RestController
@RequiredArgsConstructor
public class NoteController {

    @Autowired
    private final NoteService service;
    @Autowired
    private final ScheduleService scheduleService;

    @PostMapping(value = "/schedule/create", consumes = "application/json", produces = "application/json")
    public void createSchedule(@RequestBody Schedule schedule) throws ExecutionException, InterruptedException {
        scheduleService.saveSchedule(schedule);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class NoteIdsList {
        private List<Long> noteIds;
    }

    @PostMapping(value = "/schedule/add/many", consumes = "application/json", produces = "application/json")
    public void addNotes(@RequestBody NoteIdsList note_ids,
                         @RequestParam("user_id") String user_id) throws ExecutionException, InterruptedException {

        Schedule schedule = scheduleService.getSchedules().stream().filter(
                s -> s.getOwnerId().equalsIgnoreCase(user_id)
        ).findFirst().orElse(null);
        assert schedule != null;
        List<DiraNote> notes = service.getNotes();

        for (Long id : note_ids.getNoteIds()) {
            DiraNote note = notes.stream().filter(
                    n -> n.getId() == id
            ).findFirst().orElse(null);
            schedule.addNote(note);
        }

        scheduleService.saveSchedule(schedule);
    }

    @GetMapping(value = "/schedule/add")
    public boolean addNote(
            @RequestParam("note_id") long note_id,
            @RequestParam("user_id") String user_id
    ) throws ExecutionException, InterruptedException {
        DiraNote note = service.getNotes().stream().filter(
                n -> n.getId() == note_id
        ).findFirst().orElse(null);
        Schedule schedule = scheduleService.getSchedules().stream().filter(
                s -> s.getOwnerId().equalsIgnoreCase(user_id)
        ).findFirst().orElse(null);
        assert schedule != null;
        assert note != null;
        schedule.addNote(note);
        scheduleService.saveSchedule(schedule);
        return true;
    }

    @GetMapping(value = "/refresh")
    public void refresh() throws ExecutionException, InterruptedException {
        List<Schedule> schedules = scheduleService.getSchedules();
        for (Schedule s : schedules) {
            s.setTasks(new ArrayList<>());
            scheduleService.saveSchedule(s);
        }
    }

    @GetMapping(value = "/schedule/get")
    public Schedule getScheduleById(@RequestParam("owner_id") String user_id) throws ExecutionException, InterruptedException {
        return scheduleService.getSchedules().stream().filter(
                s -> s.getOwnerId().equalsIgnoreCase(user_id)
        ).findFirst().orElse(null);
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public void createNote(@RequestBody DiraNote note) throws ExecutionException, InterruptedException {
        service.saveNote(note);
    }

    @GetMapping("/get/all")
    public List<DiraNote> getAll() throws ExecutionException, InterruptedException {
        return service.getNotes();
    }
}
