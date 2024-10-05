package com.interview.worldcup;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ScoreBoard {

    List<Game> runningGames = new ArrayList<>();

    public List<Game> startGame(final Team homeTeam, final Team awayTeam) {
        Game game = new Game(homeTeam, awayTeam);
        runningGames.add(game);
        return runningGames;
    }

    public List<Game> finishGame(final Team homeTeam, final Team awayTeam) {
        runningGames.removeIf(g -> g.getHomeTeam().getName().equals(homeTeam.getName()) && g.getAwayTeam().getName().equals(awayTeam.getName()));
        return runningGames;
    }

    public Game updateGameScore(final Team homeTeam, final Team awayTeam, final Integer homeScore, final Integer awayScore) {
        Game gameToUpdate = runningGames.stream()
                .filter(g -> g.getHomeTeam().getName().equals(homeTeam.getName()) && g.getAwayTeam().getName().equals(awayTeam.getName()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Game between " + homeTeam.getName() + " and " + awayTeam.getName() + " not found."));

        return gameToUpdate.updateScore(homeScore, awayScore);
    }

    public List<String> getSummary() {
        return runningGames.stream()
                .sorted((g1, g2) -> {
                    final int totalScore1 = g1.getHomeScore().getScore() + g1.getAwayScore().getScore();
                    final int totalScore2 = g2.getHomeScore().getScore() + g2.getAwayScore().getScore();

                    if (totalScore1 == totalScore2) {
                        return g2.getCreationTime().compareTo(g1.getCreationTime());
                    } else {
                        return Integer.compare(totalScore2, totalScore1);
                    }
                })
                .map(game -> String.format("%s %d - %s %d",
                        game.getHomeTeam().getName(),
                        game.getHomeScore().getScore(),
                        game.getAwayTeam().getName(),
                        game.getAwayScore().getScore()))
                .toList();
    }
}
