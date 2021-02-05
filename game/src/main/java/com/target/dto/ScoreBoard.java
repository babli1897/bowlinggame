package com.target.dto;

import com.target.model.Player;
import lombok.Data;

import java.util.List;

@Data
public class ScoreBoard {

    private Long gameId;

    private Long laneId;

    private List<Player> playerDetails;
}
