package com.target.controllers;

import com.target.dto.*;
import com.target.service.GameService;
import com.target.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private RequestValidator validator;

    /* This API initializes a Game on the basis of request*/
    @RequestMapping(method = RequestMethod.POST,value = "/start/game")
    public StartGameResponse startGame(@RequestParam String requestId, @RequestBody StartGameRequest startGameRequest)
    {
        validator.validateStartGameRequest(startGameRequest);
        return gameService.initializeGame(startGameRequest);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/current/scoreboard")
    public ScoreBoardResponse getCurrentScoreBoard(@RequestParam String requestId)
    {
        return gameService.getCurrentScores();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/score")
    public ScorePerPlayer getScoreOfPlayer(@RequestParam Long gameId, @RequestParam String playerName)
    {
        return gameService.getDetailsOfPlayer(gameId,playerName);
    }
}
