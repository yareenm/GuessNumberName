package com.sal.guessnumber.dao;

import com.sal.guessnumber.entity.Round;
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
public class RoundDaoDB implements RoundDao{

    @Autowired
    private JdbcTemplate jdbc;

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("id"));
            round.setGameId(rs.getInt("gameID"));
            round.setGuess(rs.getString("guess"));

            Timestamp timestamp = rs.getTimestamp("timeStamp");
            round.setGuessTime(timestamp);

            round.setResult(rs.getString("guessResult"));
            return round;
        }
    }

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        try {
            final String SELECT_ROUNDS_BY_GAMEID = "SELECT * FROM rounds "
                    + "WHERE gameID = ? ORDER BY timeStamp;";
            List<Round> rounds = jdbc.query(SELECT_ROUNDS_BY_GAMEID, new RoundMapper(), gameId);
            return rounds;
        } catch(DataAccessException ex) {
            return null;
        }

    }

    @Override
    public Round getRoundById(int roundId) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM rounds WHERE id = ?;";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), roundId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Round addRound(Round round) {
        final String sql = "INSERT INTO Rounds(gameID, timestamp, guess, guessResult)" +
                " VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getGameId());
            statement.setTimestamp(2, round.getGuessTime());
            statement.setString(3, round.getGuess());
            statement.setString(4, round.getResult());
            return statement;

        }, keyHolder);

        round.setRoundId(keyHolder.getKey().intValue());

        return round;
    }

    @Override
    public boolean deleteById(int id) {
        final String DELETE_BY_ID = "DELETE FROM Rounds WHERE id = ?;";
        return jdbc.update(DELETE_BY_ID, id) > 0;
    }

    @Override
    public List<Round> getAllRounds() {
        try {
            final String SELECT_ALL_ROUNDS = "SELECT * FROM rounds;";
            List<Round> rounds = jdbc.query(SELECT_ALL_ROUNDS, new RoundMapper());
            return rounds;
        } catch(DataAccessException ex) {
            return null;
        }

    }

}
