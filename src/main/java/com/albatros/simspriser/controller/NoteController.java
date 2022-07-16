package com.albatros.simspriser.controller;

import com.albatros.simspriser.domain.DiraNote;
import com.albatros.simspriser.domain.NoteIdsList;
import com.albatros.simspriser.domain.Schedule;
import com.albatros.simspriser.service.NoteService;
import com.albatros.simspriser.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@RequestMapping("/note")
@RestController
@Api(tags = {"NoteController"}, description = "Minimum set of CRUD operations with notes data object")
@RequiredArgsConstructor
public class NoteController {

    @Autowired
    private final NoteService noteService;

    @Autowired
    private final ScheduleService scheduleService;

    @ApiOperation(value = "Adds all notes which ids are present in request body " +
                    "to schedule of user with requested tokenId")
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public void addNotes(
            @RequestBody NoteIdsList notesIdList,
            @RequestParam("user_id") String user_id
    ) throws ExecutionException, InterruptedException {
        Schedule schedule = scheduleService.findScheduleByOwnerId(user_id);
        List<DiraNote> notes = noteService.findInIdList(notesIdList.getNotes());
        schedule.addTaskList(notes);
        scheduleService.updateSchedule(schedule);
    }

    @ApiOperation(value = "Returns sublist of all notes with edge indexes present in request params")
    @GetMapping("/get")
    public List<DiraNote> getPaged(
            @RequestParam("from") int from,
            @RequestParam("to") int to
    ) throws ExecutionException, InterruptedException {
        List<DiraNote> all = noteService.getAllPaged(to);
        return all.stream().skip(from).collect(Collectors.toList());
    }

    @ApiOperation(value = "Saving note to the database")
    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public void createNote(@RequestBody DiraNote note) throws ExecutionException, InterruptedException {
        noteService.saveNote(note);
    }

    @ApiOperation(value = "Returns list of all available notes in the database")
    @GetMapping("/get/all")
    public List<DiraNote> getAll() throws ExecutionException, InterruptedException {
        return noteService.getNotes();
    }
}
