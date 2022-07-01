package com.albatros.simspriser.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyNote {
    private String title;
    private String description;
    private int givenScore;
    private long id;
}
