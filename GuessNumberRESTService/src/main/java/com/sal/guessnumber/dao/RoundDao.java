package com.sal.guessnumber.dao;

import com.sal.guessnumber.entity.Round;

import java.util.List;

public interface RoundDao {
    List<Round> getAllRoundsByGameId(int gameId);
    Round getRoundById(int roundId);
    Round addRound(Round round);

    boolean deleteById(int id);

    List<Round> getAllRounds();
}
