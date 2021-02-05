package com.target.dto;

import lombok.Data;

@Data
public class ScorePerPlayer {

    private Long gameId;

    private Long playerId;

    private String playerName;

    private Long currentScore;

    private Long missedStrikes;

    private Long totalStrikes;
}
