package com.sal.guessnumber.controllers;

import com.sal.guessnumber.entity.Game;
import com.sal.guessnumber.entity.Round;
import com.sal.guessnumber.service.GuessNumberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guessgame")
public class GuessNumberController {
    @Autowired
    GuessNumberServiceImpl service;

    public GuessNumberController(){}

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game beginGame(){
        return service.addGame();
    }

    @PostMapping("/guess") // ++ reult is true
    public Round guessGame(@RequestBody Round round){
        return service.makeGuess(round);
    }

    @GetMapping("/game") // ++
    public List<Game> getAllGames(){
        return service.getAllGames();
    }

    @GetMapping("/game/{id}") // ++
    public Game getGameById(@PathVariable int id){
        return service.getGameById(id);
    }

    @GetMapping("/rounds/{id}") // ++ 2 tane döndü
    public List<Round> getAllRounds(@PathVariable int id){
        return service.getAllRoundsByGameId(id);
    }

    @GetMapping("game/delete/{id}")
    public Game deleteGameById(@PathVariable int id){return service.deleteGameById(id);}
}
