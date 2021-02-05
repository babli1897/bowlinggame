package com.target.dataservice;

import com.target.model.GameMoves;
import com.target.repository.MovesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovesDataService {

    @Autowired
    private MovesRepository movesRepository;

    public GameMoves getPreviousMove(Long gameId, Long playerId)
    {
        return movesRepository.findTop1ByGameIdAndPlayerIdOrderByIdDesc(gameId,playerId);
    }

    public GameMoves saveCurrentMove(GameMoves move)
    {
        return movesRepository.save(move);
    }

    public Long countByGameIdAndPlayerId(Long gameId, Long playerId)
    {
        return movesRepository.countByGameIdAndPlayerId(gameId,playerId);
    }

    public Long getCountOfMissedStrikes(Long gameId, Long playerId, Long noOfPinBalls)
    {
        return movesRepository.getMissedStrikesCount(gameId,playerId,noOfPinBalls);
    }
}
