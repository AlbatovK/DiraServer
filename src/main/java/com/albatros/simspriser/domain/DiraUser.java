package com.albatros.simspriser.domain;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "POJO class that represents user in application")
public class DiraUser {

    private String tokenId;
    private int score = 0;
    private int scoreOfDay = 0;
    private int scoreOfWeek = 0;
    private int league = 0;
    private String email;
    private String nickname;
    private List<String> friendsIds = new ArrayList<>();

    public DiraUser(String tokenId, int score, int scoreOfDay, int scoreOfWeek, int league, String email, String nickname) {
        this.tokenId = tokenId;
        this.score = score;
        this.scoreOfDay = scoreOfDay;
        this.scoreOfWeek = scoreOfWeek;
        this.league = league;
        this.email = email;
        this.nickname = nickname;
    }
}
