package com.albatros.simspriser.domain;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "POJO class that represents note or task in application")
public class DiraNote {
    private String title;
    private String description;
    private int score;
    private boolean finished;
    private long id;
}
