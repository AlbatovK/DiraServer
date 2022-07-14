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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequestMapping("/note")
@RestController
@RequiredArgsConstructor
public class NoteController {

    @Autowired
    private final NoteService noteService;

    @Autowired
    private final ScheduleService scheduleService;

    @PostMapping(value = "/schedule/create", consumes = "application/json", produces = "application/json")
    public void saveSchedule(@RequestBody Schedule schedule) throws ExecutionException, InterruptedException {
        scheduleService.saveSchedule(schedule);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class NoteIdsList {
        private List<Long> notes;
    }

    @PostMapping(value = "/schedule/add/many", consumes = "application/json", produces = "application/json")
    public void addNotes(
            @RequestBody NoteIdsList notesIdList,
            @RequestParam("user_id") String user_id
    ) throws ExecutionException, InterruptedException {
        Schedule schedule = scheduleService.findScheduleByOwnerId(user_id);
        List<DiraNote> notes = noteService.findInIdList(notesIdList.getNotes());
        schedule.addTaskList(notes);
        scheduleService.updateSchedule(schedule);
    }

    @GetMapping(value = "/refresh")
    public void refresh() throws ExecutionException, InterruptedException {
        List<Schedule> schedules = scheduleService.getSchedules();
        for (Schedule schedule : schedules) {
            schedule.clearTasks();
            scheduleService.updateSchedule(schedule);
        }
    }

    @GetMapping(value = "/schedule/get")
    public Schedule getScheduleById(@RequestParam("owner_id") String user_id) throws ExecutionException, InterruptedException {
        return scheduleService.findScheduleByOwnerId(user_id);
    }

    @GetMapping("/get")
    public List<DiraNote> getPaged(
            @RequestParam("from") int from,
            @RequestParam("to") int to
    ) throws ExecutionException, InterruptedException {
        List<DiraNote> all = noteService.getAllPaged(to);
        return all.stream().skip(from).collect(Collectors.toList());
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public void createNote(@RequestBody DiraNote note) throws ExecutionException, InterruptedException {
        noteService.saveNote(note);
    }

    @GetMapping("/get/all")
    public List<DiraNote> getAll() throws ExecutionException, InterruptedException {
        return noteService.getNotes();
    }
}
