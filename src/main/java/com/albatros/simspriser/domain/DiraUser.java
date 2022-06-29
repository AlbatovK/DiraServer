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
    private String email;
    private String password;
    private String nickname;
    private List<String> friendsIds = new ArrayList<>();

    public DiraUser(String tokenId, long score, String email, String password, String nickname) {
        this.tokenId = tokenId;
        this.score = score;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
