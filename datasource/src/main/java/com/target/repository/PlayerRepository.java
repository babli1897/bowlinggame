package com.target.repository;

import com.target.model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player,Long> {

    List<Player> findByGameId(Long gameId);

    Player findByGameIdAndPlayerName(Long gameId,String playerName);
}
