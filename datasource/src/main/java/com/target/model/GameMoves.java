package com.target.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "moves")
public class GameMoves {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "pins_down")
    private int pinsDown;

    @Column(name = "previous_move_id")
    private Long previousMoveId;

    @Column(name = "spare")
    private boolean spare;

    @Column(name = "strike")
    private boolean strike;

    @Column(name = "set_number")
    private Long setNumber;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
}
