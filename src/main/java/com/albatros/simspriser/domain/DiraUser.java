package com.albatros.simspriser.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiraUser {

    private String tokenId;
    private long score = 0;
    private long scoreOfDay = 0;
    private long scoreOfWeek = 0;
    private String email;
    private String nickname;
    private List<String> friendsIds = new ArrayList<>();

    public DiraUser(String tokenId, long score, long scoreOfDay, long scoreOfWeek, String email, String nickname) {
        this.tokenId = tokenId;
        this.score = score;
        this.scoreOfDay = scoreOfDay;
        this.scoreOfWeek = scoreOfWeek;
        this.email = email;
        this.nickname = nickname;
    }
}
