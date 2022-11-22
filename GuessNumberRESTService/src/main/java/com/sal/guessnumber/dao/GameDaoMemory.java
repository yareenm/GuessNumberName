package com.sal.guessnumber.dao;

import com.sal.guessnumber.entity.Game;

import java.util.*;

public class GameDaoMemory implements GameDao {

    private static final List<Game> games = new ArrayList<>();

    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }

    @Override
    public Game getGameById(int gameId) {
        return games.stream()
                .filter(i -> i.getGameId() == gameId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Game addGame(Game game) {
        int nextId = games.stream()
                .mapToInt(Game::getGameId)
                .max()
                .orElse(0) + 1;

        game.setGameId(nextId);
        games.add(game);
        return game;
    }

    @Override
    public boolean updateGame(Game game) {
        int index = 0;
        while (index < games.size()
                && games.get(index).getGameId() != game.getGameId()) {
            index++;
        }

        if (index < games.size()) {
            games.set(index, game);
        }
        return index < games.size();
    }

    @Override
    public boolean deleteById(int id) {
        return games.removeIf(i -> i.getGameId() == id);
    }
}
