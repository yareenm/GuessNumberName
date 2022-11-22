package com.sal.guessnumber.dao;

import com.sal.guessnumber.entity.Game;

import java.util.List;

public interface GameDao {
    List<Game> getAllGames();
    Game getGameById(int gameId);
    Game addGame(Game game);
    boolean updateGame(Game game);

    boolean deleteById(int id);
}
