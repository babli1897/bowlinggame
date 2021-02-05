package com.target.dto;

import lombok.Data;

import java.util.List;

@Data
public class StartGameRequest {

    private List<String> playerNames;

}
