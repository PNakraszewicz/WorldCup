package com.interview.worldcup;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ScoreBoard {

    List<Game> runningGames = new ArrayList<>();

    public List<Game> startGame(final Team homeTeam, final Team awayTeam) {
        Game game = new Game(homeTeam, awayTeam);
        runningGames.add(game);
        return runningGames;
    }

    public List<Game> finishGame(final Team homeTeam, final Team awayTeam) {
        return runningGames;
    }
}
