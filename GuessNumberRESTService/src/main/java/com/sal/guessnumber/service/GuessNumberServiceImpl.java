package com.sal.guessnumber.service;

import com.sal.guessnumber.dao.*;
import com.sal.guessnumber.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Service
public class GuessNumberServiceImpl{

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    // this function is for get all games
    public List<Game> getAllGames(){
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            if (!game.isFinished()) {
                game.setAnswer("****");
            }
        }

        return games;
    }

    // this function is for get all rounds by its related game id
    public List<Round> getAllRoundsByGameId(int gameId){
        return roundDao.getAllRoundsByGameId(gameId);
    }

    // this function is for determine if the user guessed correctly or not.
    // also, it is generating the result for the comparison.
    // if guess and answer matches it is setting the isfinished property to true.
    public Round makeGuess(Round round){

        String answer = gameDao.getGameById(round.getGameId()).getAnswer();
        String guess = round.getGuess();
        Round round2= determineResult(round,guess, answer);

        if (guess.equals(answer)) {
            Game game = game = getGameById(round2.getGameId());
            game.setFinished(true);
            game.setAnswer(answer);
            gameDao.updateGame(game);
        }
        setTimeStamp(round2);
        return roundDao.addRound(round2);
    }

    // this function is for get the game by its related id
    public Game getGameById(int gameId) {
        Game game = gameDao.getGameById(gameId);
        if (!game.isFinished()) {
            game.setAnswer("****");
        }
        return game;
    }

    // this function is for delete a game by its related id
    public Game deleteGameById(int gameId){
        Game game=gameDao.getGameById(gameId);
        if(gameDao.deleteById(game.getGameId())){
            return game;
        }
        else{
            return null;
        }
    }
    // this function is for create a new game for test
    public Game newGame() {
        Game game = new Game();
        Random rand = new Random();

        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int num = rand.nextInt(10);
            answer.append(num);

        }
        game.setAnswer(answer.toString());
        game.setFinished(false);
        return game;
    }
    // this function is for create a  new game
    public Game addGame(){
        Game game = new Game();
        game.setAnswer(generateAnswer());
        game.setFinished(false);
        game = gameDao.addGame(game);

        game.setAnswer("****");
        return game;
    }

    // it is a helper function for generate a random answer for the game
    private String generateAnswer(){
        Random rnd = new Random();
        int digit1 = rnd.nextInt(10);

        int digit2 = rnd.nextInt(10);
        while (digit2 == digit1) {
            digit2 = rnd.nextInt(10);
        }

        int digit3 = rnd.nextInt(10);
        while (digit3 == digit2 || digit3 == digit1) {
            digit3 = rnd.nextInt(10);
        }

        int digit4 = rnd.nextInt(10);
        while (digit4 == digit3 || digit4 == digit2 || digit4 == digit1) {
            digit4 = rnd.nextInt(10);
        }

        String answer = String.valueOf(digit1) + String.valueOf(digit2)
                + String.valueOf(digit3) + String.valueOf(digit4);

        return answer;


    }


    // it is a helper function for inspecting the answer and
    // find out the number of exact parts and the number of partial parts
    public Round determineResult(Round round2,String guess, String answer) {
        Round round = round2;
        round.setGuess(guess);
        char[] guessChars = guess.toCharArray();
        char[] answerChars = answer.toCharArray();
        int exact = 0, partial = 0;


        if (guess.length() != 4) {
            String result2 = "e:" + exact + ":p:" + partial;
            round.setGuess(result2);
            return round;
        }
        else {
            for (int i = 0; i < guessChars.length; i++) {

                if (answer.indexOf(guessChars[i]) > -1) {
                    if (guessChars[i] == answerChars[i]) {
                        exact++;
                    } else {
                        partial++;
                    }
                }
            }
        }

        String result = "e:" + exact + ":p:" + partial;

        round.setResult(result);

        return round;
    }

    public void setTimeStamp(Round round) {
        Calendar calendar = Calendar.getInstance();
        Timestamp guessTime = new Timestamp(calendar.getTime().getTime());

        round.setGuessTime(guessTime);
    }

}
