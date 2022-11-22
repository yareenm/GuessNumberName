package com.sal.guessnumber.dao;

import com.sal.guessnumber.TestApplicationConfiguration;
import com.sal.guessnumber.entity.Game;
import com.sal.guessnumber.entity.Round;
import com.sal.guessnumber.service.GuessNumberServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDaoDBTest {

    @Autowired
    GameDao gameDao;
    @Autowired
    RoundDao roundDao;

    public RoundDaoDBTest () {}

    @Before
    public void setUp() {

        List<Round> rounds = roundDao.getAllRounds();
        for(Round round : rounds) {
            roundDao.deleteById(round.getRoundId());
        }

        List<Game> games = gameDao.getAllGames();
        for(Game game : games) {
            gameDao.deleteById(game.getGameId());
        }
    }

    @Test
    public void testAdd() {
        GuessNumberServiceImpl gameService = new GuessNumberServiceImpl();
        Game game = gameService.newGame();
        gameDao.addGame(game);

        Round round = new Round();
        round.setGameId(game.getGameId());
        gameService.setTimeStamp(round);
        round.setGuess("1234");
        round.setResult("e:2:p:1");
        roundDao.addRound(round);
        Round fromDao = roundDao.getRoundById(round.getRoundId());

        assertEquals(round.getRoundId(), fromDao.getRoundId());
    }

    @Test
    public void testGetAll() {
        GuessNumberServiceImpl gameService = new GuessNumberServiceImpl();
        Game game = gameService.newGame();
        gameDao.addGame(game);

        Game game2 = gameService.newGame();
        gameDao.addGame(game2);

        Round round = new Round();
        round.setGuess("1111");
        round.setGameId(game.getGameId());

        Round round2 = new Round();
        round2.setGuess("2222");
        round2.setGameId(game2.getGameId());

        roundDao.addRound(round);
        roundDao.addRound(round2);

        List<Round> rounds = roundDao.getAllRounds();
        assertEquals(2, rounds.size());
    }

    @Test
    public void testGetAllOfGame() {
        //implement
        GuessNumberServiceImpl gameService = new GuessNumberServiceImpl();
        Game game = gameService.newGame();
        gameDao.addGame(game);

        Game game2 = gameService.newGame();
        gameDao.addGame(game2);

        Round round = new Round();
        round.setGuess("1111");
        round.setGameId(game.getGameId());

        Round round2 = new Round();
        round2.setGuess("2222");
        round2.setGameId(game2.getGameId());

        roundDao.addRound(round);
        roundDao.addRound(round2);


        List<Round> rounds1 = roundDao.getAllRoundsByGameId(round.getGameId());
        List<Round> rounds2 = roundDao.getAllRoundsByGameId(round2.getGameId());

        assertTrue("Round list 1 should include game id 1",rounds1.contains(game.getGameId()));
        assertTrue("Round list 2 should include game id 2",rounds2.contains(game2.getGameId()));

    }
}