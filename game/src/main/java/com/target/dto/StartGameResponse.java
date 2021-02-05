package com.target.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StartGameResponse {

    String responseCode;

    String respMsg;

    String status;

    List<Long> gameId = new ArrayList<>();
}
