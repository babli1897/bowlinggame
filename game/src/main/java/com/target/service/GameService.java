package com.target.service;

import com.target.dataservice.GameDataService;
import com.target.dataservice.MovesDataService;
import com.target.dataservice.PlayerDataService;
import com.target.dataservice.PropertiesDataService;
import com.target.dto.*;
import com.target.enums.Enums;
import com.target.exceptions.InvalidRequestException;
import com.target.model.Game;
import com.target.model.GameMoves;
import com.target.model.Lane;
import com.target.model.Player;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.target.enums.Enums.Properties.NUMBER_OF_PLAYERS_PER_LANE;
import static com.target.enums.Enums.ResponseConstant.NOT_ENOUGH_FREE_LANES;

@Service
@Slf4j
public class GameService {

    @Autowired
    private GameDataService gameDataService;

    @Autowired
    private PlayerDataService playerDataService;

    @Autowired
    private MovesDataService movesDataService;

    @Autowired
    private PropertiesDataService propertiesDataService;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public StartGameResponse initializeGame(StartGameRequest request)
    {
        StartGameResponse response = new StartGameResponse();
        List<String> players = request.getPlayerNames();

        CompletionService<Boolean> executorCompletionService = new ExecutorCompletionService(executorService);
        List<Future> futures = new ArrayList<>();

        List<Game> gameList = createGame(players);
        gameList.forEach(game -> {
                ThreadForEachGame threadForEachGame = new ThreadForEachGame();
                threadForEachGame.setGame(game);
                threadForEachGame.setPlayers(playerDataService.findByGameId(game.getId()));
                futures.add(executorCompletionService.submit(threadForEachGame));
                response.getGameId().add(game.getId());
            }
        );

        for (int i=0; i<futures.size(); i++) {
            try {
                Boolean result = executorCompletionService.take().get();
            } catch (InterruptedException e) {
                log.error("InterruptedException occurred", e);
            } catch (ExecutionException e) {
                log.error("ExecutionException occurred", e);
            }
        }
        response.setStatus(Enums.ResponseStatus.SUCCESS.name());
        response.setRespMsg("Game Initialized");
        return response;

    }

    private List<Game> createGame(List<String> playerNames)
    {
        List<Game> games = new ArrayList<>();
        int noOfPlayersPerLane = Integer.parseInt(propertiesDataService.getPropertyValueByName(NUMBER_OF_PLAYERS_PER_LANE.name()));
        int getFreeLanes = gameDataService.getFreeLanes();
        if((playerNames.size()%noOfPlayersPerLane)==0 && getFreeLanes>=playerNames.size()/noOfPlayersPerLane || (playerNames.size()/noOfPlayersPerLane+1<=getFreeLanes)) {
            List<Player> players = playerNames.stream().map(name -> new Player(name)).collect(Collectors.toList());
            for(int i=0;i<playerNames.size();i+=noOfPlayersPerLane) {
                Game game = new Game();

                game.setStatus(Enums.GameStatus.INITIALIZED.name());
                int startPlayerIndex = i;
                int endPlayerIndex = (i+noOfPlayersPerLane>playerNames.size()?playerNames.size():i+noOfPlayersPerLane);
                List<Player> playersPerLane = players.subList(startPlayerIndex,endPlayerIndex);
                game.setPlayersCount(playersPerLane.size());
                Lane freeLane = gameDataService.getFreeLane();
                if(freeLane==null)
                {
                    throw new InvalidRequestException(NOT_ENOUGH_FREE_LANES.name());
                }
                gameDataService.createGame(game, playersPerLane,freeLane);
                games.add(game);
            }
            return games;
        }
        else
            throw new InvalidRequestException(NOT_ENOUGH_FREE_LANES.name());
    }

    public ScoreBoardResponse getCurrentScores()
    {
        ScoreBoardResponse response = new ScoreBoardResponse();

        List<Game> games = gameDataService.findGamesByStatus(Enums.GameStatus.PLAYING.name());
        if(CollectionUtils.isEmpty(games))
        {
            response.setStatus(Enums.ResponseStatus.FAILURE.name());

        }
        List<ScoreBoard> scoreBoards = new ArrayList<>();
        games.stream().forEach(game -> {
            ScoreBoard board = new ScoreBoard();
            board.setGameId(game.getId());
            board.setLaneId(game.getLaneNumber());
            List<Player> players = playerDataService.findByGameId(game.getId());
            board.setPlayerDetails(players);
            scoreBoards.add(board);

        });
        response.setScoreBoards(scoreBoards);
        return response;
    }

    @Data
    public class ThreadForEachGame implements Callable
    {
        private Game game;

        private List<Player> players;

        @Override
        public Object call() throws Exception{

            int totalNoOfSets = Integer.parseInt(propertiesDataService.getPropertyValueByName(Enums.Properties.TOTAL_NUMBER_OF_SETS.name()));
            int chancePerSet = Integer.parseInt(propertiesDataService.getPropertyValueByName(Enums.Properties.CHANCE_PER_SET.name()));
            int noOfPinBalls = Integer.parseInt(propertiesDataService.getPropertyValueByName(Enums.Properties.NUMBER_OF_PIN_BALLS_PER_SET.name()));
            int bonusScoreForStrike = Integer.parseInt(propertiesDataService.getPropertyValueByName(Enums.Properties.BONUS_SCORE_FOR_STRIKE.name()));
            int bonusScoreForSpare = Integer.parseInt(propertiesDataService.getPropertyValueByName(Enums.Properties.BONUS_SCORE_FOR_SPARE.name()));
            int maxChanceForLastSet = Integer.parseInt(propertiesDataService.getPropertyValueByName(Enums.Properties.MAXIMUM_CHANCE_FOR_LAST_SET.name()));

            game.setStatus(Enums.GameStatus.PLAYING.name());
            gameDataService.saveGame(game);
            Random random = new Random();
            for(int i=0;i<totalNoOfSets;i++)
            {
                for(Player player : players)
                {
                    boolean strike = false, spare = false;
                    int currChance = 1, totalPinsDown = 0;
                    while (!strike && !spare && currChance++<=chancePerSet)
                    {
                        int pinsDown = random.nextInt(noOfPinBalls+1);
                        totalPinsDown += pinsDown;
                        strike = isStrike(pinsDown,noOfPinBalls);
                        spare = isSpare(totalPinsDown,noOfPinBalls);
                        saveGameMove(game,player,spare,strike,pinsDown);

                    }
                    if(!strike)
                    {
                        spare = isSpare(totalPinsDown,noOfPinBalls);
                    }
                    if(strike)
                    {
                        player.setCurrentScore(player.getCurrentScore()+noOfPinBalls+bonusScoreForStrike);
                    }
                    else if(spare)
                    {
                        player.setCurrentScore(player.getCurrentScore()+noOfPinBalls+bonusScoreForSpare);
                    }
                    else
                    {
                        player.setCurrentScore(player.getCurrentScore()+totalPinsDown);
                    }
                    if(isLastSet(i,totalNoOfSets) && (strike||spare))
                    {
                        while (currChance++<=maxChanceForLastSet)
                        {
                            int pinsDown = random.nextInt(noOfPinBalls+1);
                            totalPinsDown += pinsDown;
                            strike = isStrike(pinsDown,noOfPinBalls);
                            spare = isSpare(totalPinsDown,noOfPinBalls);
                            saveGameMove(game,player,spare,strike,pinsDown);
                        }
                    }
                    playerDataService.updatePlayer(player);
                }
            }
            gameDataService.freeLane(game);
            game.setStatus(Enums.GameStatus.COMPLETED.name());
            gameDataService.saveGame(game);
            return true;
        }
    }

    private void saveGameMove(Game game, Player player, Boolean spare, Boolean strike,int pinsDown)
    {
        GameMoves move = new GameMoves();
        move.setGameId(game.getId());
        move.setPlayerId(player.getId());
        move.setPinsDown(pinsDown);
        move.setSetNumber(game.getLaneNumber());
        move.setSpare(spare);
        move.setStrike(strike);
        GameMoves prevMove = movesDataService.getPreviousMove(game.getId(),player.getId());
        if(prevMove!=null)
            move.setPreviousMoveId(prevMove.getId());
        else
            move.setPreviousMoveId(0l);
        movesDataService.saveCurrentMove(move);
    }
    private boolean isStrike(int currScore,int noOfPinBallsPerSet)
    {
        return currScore==noOfPinBallsPerSet;
    }

    private boolean isSpare(int totalScore,int noOfPinBallsPerSet)
    {
        return totalScore>=noOfPinBallsPerSet;
    }

    private boolean isLastSet(int currentSet, int totalSet)
    {
        return currentSet == totalSet-1;
    }

    public ScorePerPlayer getDetailsOfPlayer(Long gameId, String playerName)
    {
        int noOfPinBalls = Integer.parseInt(propertiesDataService.getPropertyValueByName(Enums.Properties.NUMBER_OF_PIN_BALLS_PER_SET.name()));
        ScorePerPlayer response = new ScorePerPlayer();
        Player player = playerDataService.findByGameIdAndName(gameId,playerName);
        if(player==null)
            throw new InvalidRequestException(Enums.ResponseConstant.NO_PLAYER_FOUND.name());
        response.setGameId(gameId);
        response.setCurrentScore(player.getCurrentScore());
        long movesCount = movesDataService.countByGameIdAndPlayerId(gameId,player.getId());
        long missedStrike = movesDataService.getCountOfMissedStrikes(gameId,player.getId(),Long.valueOf(noOfPinBalls));
        response.setTotalStrikes(movesCount);
        response.setMissedStrikes(missedStrike);
        response.setPlayerName(playerName);
        return response;
    }
}
