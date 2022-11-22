package com.sal.guessnumber.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Round {
    private int roundId;
    private int gameId;
    private Timestamp guessTime;
    private String guess;
    private String result;

    public Round() {
    }

    public Round(int gameId, String guess) {
        this.gameId = gameId;
        this.guess = guess;
    }


    public Round(int roundId, int gameId, Timestamp guessTime, String guess, String result) {
        this.roundId = roundId;
        this.gameId = gameId;
        this.guessTime = guessTime;
        this.guess = guess;
        this.result = result;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Timestamp getGuessTime() {
        return guessTime;
    }

    public void setGuessTime(Timestamp guessTime) {
        this.guessTime = guessTime;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return roundId == round.roundId && gameId == round.gameId && Objects.equals(guessTime, round.guessTime) && Objects.equals(guess, round.guess) && Objects.equals(result, round.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, gameId, guessTime, guess, result);
    }
}
