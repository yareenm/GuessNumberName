package com.sal.guessnumber.dao;


import com.sal.guessnumber.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
public class GameDaoDB implements GameDao {

    @Autowired
    private final JdbcTemplate jdbc;

    public GameDaoDB (JdbcTemplate jdbc){this.jdbc=jdbc;}
    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("id"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("isFinished"));
            return game;
        }
    }

    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT* FROM games; ";
        return jdbc.query(SELECT_ALL_GAMES,new GameMapper());
    }

    @Override
    public Game getGameById(int gameId) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM games WHERE id=?; ";
            return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameId);
        }catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    @Transactional
    public Game addGame(Game game) {
        final String sql = "INSERT INTO games(answer, isFinished) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());
            statement.setBoolean(2, game.isFinished());
            return statement;

        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
    }

    @Override
    public boolean updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE Games SET answer = ? , isFinished = ? WHERE id = ?;";
        return jdbc.update(UPDATE_GAME, game.getAnswer(),game.isFinished(), game.getGameId())>0;

    }

    @Override
    public boolean deleteById(int id) {
        final String DELETE_BY_ID = "DELETE FROM Games WHERE id = ?;";
        return jdbc.update(DELETE_BY_ID, id) > 0;
    }
}
