package com.target.dataservice;

import com.target.model.Player;
import com.target.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerDataService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> findByGameId(Long gameId)
    {
        return playerRepository.findByGameId(gameId);
    }

    public Player findByGameIdAndName(Long gameId, String name)
    {
        return playerRepository.findByGameIdAndPlayerName(gameId,name);
    }

    public Player updatePlayer(Player player)
    {
        return playerRepository.save(player);
    }
}
