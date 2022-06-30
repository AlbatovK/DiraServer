package com.albatros.simspriser.rest.controller;

import com.albatros.simspriser.domain.DiraNote;
import com.albatros.simspriser.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequestMapping("/note")
@RestController
@RequiredArgsConstructor
public class NoteController {

    @Autowired
    private final NoteService service;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public void createQuiz(@RequestBody DiraNote note) throws ExecutionException, InterruptedException {
        service.saveNote(note);
    }

    @GetMapping("/get/all")
    public List<DiraNote> getAll(@RequestParam("user_id") String user_id) throws ExecutionException, InterruptedException {
        return service.getNotes().stream().filter(
                note -> note.getOwnerId().equalsIgnoreCase(user_id)
        ).collect(Collectors.toList());
    }
}
