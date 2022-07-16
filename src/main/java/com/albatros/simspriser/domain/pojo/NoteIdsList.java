package com.albatros.simspriser.domain.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "POJO class used for transferring list of notes ids")
public class NoteIdsList {
    private List<Long> notes;
}