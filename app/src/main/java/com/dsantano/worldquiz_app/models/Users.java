package com.dsantano.worldquiz_app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private int gamesPlayed;
    private String name;
    private String photo;
    private int score;
    private String uid;
}
