package com.albatros.simspriser.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiraNote {
    private String title;
    private String description;
    private List<Task> tasks;
    private Date date;
    private boolean finished;
    private long id;
    private String ownerId;
}
