package com.target.dataservice;

import com.target.model.Game;
import com.target.model.Lane;
import com.target.model.Player;
import com.target.repository.GameRepository;
import com.target.repository.LaneRepository;
import com.target.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameDataService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private LaneRepository laneRepository;

    private static final String freeLaneStatus = "FREE";

    private static final String occupiedLaneStatus = "OCCUPIED";

    public void createGame(Game game, List<Player> playerList, Lane freeLane)
    {
        game.setLaneNumber(freeLane.getId());
        freeLane.setLaneStatus(occupiedLaneStatus);
        laneRepository.save(freeLane);
        gameRepository.save(game);

        playerList.stream().forEach(player -> {player.setGameId(game.getId());
            player.setCurrentScore(0l);});
        playerRepository.saveAll(playerList);
    }

    public void freeLane(Game game)
    {
        Optional<Lane> freeLane = laneRepository.findById(game.getLaneNumber());

        Lane currLane = freeLane.get();
        currLane.setLaneStatus(freeLaneStatus);
        laneRepository.save(currLane);

    }

    public Lane getFreeLane()
    {
        return laneRepository.findTop1ByLaneStatusOrderByIdAsc(freeLaneStatus);
    }
    public List<Game> findGamesByStatus(String status)
    {
        return gameRepository.findByStatus(status);
    }

    public void saveGame(Game game)
    {
        gameRepository.save(game);
    }

    public int getFreeLanes()
    {
        return laneRepository.countByLaneStatus(freeLaneStatus);
    }
}
