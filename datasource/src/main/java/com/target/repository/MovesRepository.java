package com.target.repository;

import com.target.model.GameMoves;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MovesRepository extends CrudRepository<GameMoves,Long> {

    GameMoves findTop1ByGameIdAndPlayerIdOrderByIdDesc(Long gameId, Long playerId);

    Long countByGameIdAndPlayerId(Long gameId, Long playerId);

    @Query(value = "select count(*) from moves where game_id = :gameId and player_id = :playerId and pins_down< :totalBalls")
    Long getMissedStrikesCount(@Param("gameId") Long gameId, @Param("playerId") Long playerId, @Param("totalBalls") Long totalBalls);
}
