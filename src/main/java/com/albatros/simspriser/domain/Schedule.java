package com.albatros.simspriser.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    private String ownerId;
    private List<DiraNote> tasks;
    private long id;

    public void clearTasks() {
        tasks.clear();
    }

    public void addTask(DiraNote note) {
        tasks.add(note);
    }
}
