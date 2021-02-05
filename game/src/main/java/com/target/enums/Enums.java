package com.target.enums;

public class Enums {

    public enum ResponseStatus
    {
        SUCCESS, FAILURE
    }

    public enum GameStatus
    {
        INITIALIZED, PLAYING, COMPLETED
    }

    public enum ResponseConstant
    {
        NO_PLAYER_FOUND, SUCCESS, NOT_ENOUGH_FREE_LANES, NULL_REQUEST_RECEIVED, INVALID_PLAYERS, INTERNAL_SYSTEM_ERROR
    }

    public enum Properties
    {
        TOTAL_NUMBER_OF_SETS, CHANCE_PER_SET, NUMBER_OF_PIN_BALLS_PER_SET, BONUS_SCORE_FOR_STRIKE, BONUS_SCORE_FOR_SPARE, NUMBER_OF_PLAYERS_PER_LANE, MAXIMUM_CHANCE_FOR_LAST_SET
    }
}
