package com.target.repository;

import com.target.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game,Long> {

    List<Game> findByStatus(String status);
}
