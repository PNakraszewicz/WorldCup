package com.interview.worldcup;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ScoreBoard {

    List<Game> runningGames = new ArrayList<>();

    public List<Game> startGame(final StartGameCommand command) {
        Game game = new Game(command.homeTeam(), command.awayTeam());
        runningGames.add(game);
        return runningGames;
    }

    public List<Game> finishGame(final FinishGameCommand command) {
        runningGames.removeIf(g -> g.getHomeTeam().name().equals(command.homeTeam().name())
                && g.getAwayTeam().name().equals(command.awayTeam().name()));
        return runningGames;
    }

    public Game updateGameScore(final UpdateGameCommand command) {
        Game gameToUpdate = runningGames.stream()
                .filter(g -> g.getHomeTeam().name().equals(command.homeTeam().name())
                        && g.getAwayTeam().name().equals(command.awayTeam().name()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Game between " + command.homeTeam().name()
                        + " and " + command.awayTeam().name() + " not found."));

        return gameToUpdate.updateScore(command.homeScore(), command.awayScore());
    }

    public List<String> getSummary() {
        return runningGames.stream()
                .sorted((g1, g2) -> {
                    final int totalScore1 = g1.getHomeScore().getScore() + g1.getAwayScore().getScore();
                    final int totalScore2 = g2.getHomeScore().getScore() + g2.getAwayScore().getScore();

                    if (totalScore1 == totalScore2) {
                        return Integer.compare(runningGames.indexOf(g2), runningGames.indexOf(g1));
                    } else {
                        return Integer.compare(totalScore2, totalScore1);
                    }
                })
                .map(game -> String.format("%s %d - %s %d",
                        game.getHomeTeam().name(),
                        game.getHomeScore().getScore(),
                        game.getAwayTeam().name(),
                        game.getAwayScore().getScore()))
                .toList();
    }
}
