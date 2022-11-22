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

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDaoDBTest {

    @Autowired
    GameDao gameDao;
    @Autowired
    RoundDao roundDao;

    public GameDaoDBTest(){}

    @Before
    public void setUp() throws Exception {

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
    public void testAddGetGames() {
        // adds new game using dao
        GuessNumberServiceImpl gameService = new GuessNumberServiceImpl();
        Game game = gameService.newGame();
        gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());
        assertEquals(game.getGameId(), fromDao.getGameId());
    }

    @Test
    public void testGetAll() {
        // adds 2 new game using dao and checks 2 of them is in the list
        GuessNumberServiceImpl gameService = new GuessNumberServiceImpl();
        Game game = gameService.newGame();
        gameDao.addGame(game);

        GuessNumberServiceImpl gameService2 = new GuessNumberServiceImpl();
        Game game2 = gameService.newGame();
        gameDao.addGame(game2);

        List<Game> gameList = gameDao.getAllGames();
        assertEquals(2,gameList.size());
        assertNotNull("Game list should not be null",gameList);

        assertTrue("Game list should include game 1",gameList.contains(game));
        assertTrue("Game list should include game 2",gameList.contains(game2));


    }


    @Test
    public void testUpdate() {
        // adds new game using dao and then update that game
        GuessNumberServiceImpl gameService = new GuessNumberServiceImpl();
        Game game = gameService.newGame();
        gameDao.addGame(game);
        game.setFinished(true);
        gameDao.updateGame(game);
        Game updated = gameDao.getGameById(game.getGameId());
        assertTrue(updated.isFinished());
    }

    @Test
    public void testDeleteById() {
        //adds new 2 game using dao and delete one of them using dao
        GuessNumberServiceImpl gameService = new GuessNumberServiceImpl();
        Game game = gameService.newGame();
        gameDao.addGame(game);

        GuessNumberServiceImpl gameService2 = new GuessNumberServiceImpl();
        Game game2 = gameService.newGame();
        gameDao.addGame(game2);

        boolean result = gameDao.deleteById(game2.getGameId());

        List<Game> gameList = gameDao.getAllGames();
        assertEquals(1, gameList.size());
        assertTrue(result);

        assertFalse("All games should not include game 2",gameList.contains(game2));
        assertTrue("All games should include game 1",gameList.contains(game));
    }
}