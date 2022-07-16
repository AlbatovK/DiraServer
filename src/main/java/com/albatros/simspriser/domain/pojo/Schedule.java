package com.albatros.simspriser.domain.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Class that represents list of tasks of specific user " +
        "and contains functionality for managing it")
public class Schedule {

    private String ownerId;
    private List<DiraNote> tasks;
    private long id;

    public void addTaskList(List<DiraNote> notes) {
        tasks.addAll(notes);
    }

    public void addTask(DiraNote note) {
        tasks.add(note);
    }

    public void clearTasks() {
        tasks.clear();
    }
}
