package com.target.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScoreBoardResponse {

    private List<ScoreBoard> scoreBoards;

    private String status;

    private String responseCode;

}
