package com.albatros.simspriser.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiraNote {
    private String title;
    private String description;
    private int score;
    private boolean finished;
    private long id;
}
