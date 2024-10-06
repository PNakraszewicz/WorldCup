package com.interview.wordlcup;

import com.interview.worldcup.Game;
import com.interview.worldcup.ScoreBoard;
import com.interview.worldcup.Team;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreBoardTest {
    @Test
    void startingGameShouldAddGameWithResultZeroZero() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        final String homeTeamName = "Argentina";
        final String awayTeamName = "Germany";
        final Team homeTeam = new Team(homeTeamName);
        final Team awayTeam = new Team(awayTeamName);
        //when
        final List<Game> games = scoreBoard.startGame(homeTeam, awayTeam);

        //then
        assertFalse(games.isEmpty());
        assertEquals(1, games.size());

        final Game game = games.stream().findFirst().get();
        assertEquals(homeTeamName, game.getHomeTeam().getName());
        assertEquals(awayTeamName, game.getAwayTeam().getName());
        assertEquals(0, game.getHomeScore().getScore());
        assertEquals(0, game.getAwayScore().getScore());
    }

    @Test
    void startingGameShouldThrowExceptionWhenTeamIsAlreadyPlayingOtherGame() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        final Team awayTeam = new Team("Germany");
        final Team secondAwayTeam = new Team("France");
        final Team teamPlayingTwoMatchesSameTime = new Team("Argentina");

        //when
        scoreBoard.startGame(teamPlayingTwoMatchesSameTime, awayTeam);

        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.startGame(teamPlayingTwoMatchesSameTime, secondAwayTeam));
        assertEquals("Team Argentina is already playing a different game.", exception.getMessage());
    }

    @Test
    void startingGameShouldThrowExceptionWhenHomeAndAwayTeamAreTheSame() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        final Team homeTeam = new Team("Croatia");
        final Team awayTeam = new Team("Croatia");

        //when
        scoreBoard.startGame(homeTeam, awayTeam);

        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.startGame(homeTeam, awayTeam));
        assertEquals("Team cannot play with itself", exception.getMessage());
    }

    @Test
    void startingGameShouldThrowExceptionWhenTeamNameIsEmpty() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        final String homeTeamName = "  ";
        final String awayTeamName = "Croatia";
        final Team homeTeam = new Team(homeTeamName);
        final Team awayTeam = new Team(awayTeamName);

        //when
        scoreBoard.startGame(homeTeam, awayTeam);

        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.startGame(homeTeam, awayTeam));
        assertEquals("Team name cannot be empty", exception.getMessage());
    }

    @Test
    void startingGameShouldThrowExceptionWhenTeamNameIsNull() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        final Team homeTeam = new Team(null);
        final Team awayTeam = new Team("Croatia");

        //when
        scoreBoard.startGame(homeTeam, awayTeam);

        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.startGame(homeTeam, awayTeam));
        assertEquals("Team name cannot be empty", exception.getMessage());
    }

    @Test
    void finishingGameShouldRemoveGameFromTheRunningGames() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        final Team homeTeam = new Team("Argentina");
        final Team awayTeam = new Team("Germany");
        //when
        scoreBoard.startGame(homeTeam, awayTeam);
        final List<Game> games = scoreBoard.finishGame(homeTeam, awayTeam);

        //then
        assertTrue(games.isEmpty());
    }

    @Test
    void finishingGameShouldThrowExceptionWhenMatchIsNotFound() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        final Team homeTeam = new Team("Argentina");
        final Team awayTeam = new Team("Germany");
        final Team otherTeam = new Team("Romania");

        //when & then
        scoreBoard.startGame(homeTeam, awayTeam);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                scoreBoard.finishGame(otherTeam, awayTeam));
        assertEquals("Game between Romania and Germany not found.", exception.getMessage());
    }

    @Test
    void updatingScoreShouldSetNewScore() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        final String homeTeamName = "Spain";
        final String awayTeamName = "England";
        final Team homeTeam = new Team(homeTeamName);
        final Team awayTeam = new Team(awayTeamName);
        //when
        scoreBoard.startGame(homeTeam, awayTeam);
        final Game game = scoreBoard.updateGameScore(homeTeam, awayTeam, 2, 1);

        //then
        assertEquals(2, game.getHomeScore().getScore());
        assertEquals(1, game.getAwayScore().getScore());
        assertEquals(homeTeamName, game.getHomeTeam().getName());
        assertEquals(awayTeamName, game.getAwayTeam().getName());
    }

    @Test
    void updatingScoreShouldThrowExceptionWhenMatchIsNotOnTheBoard() {
        //given
        ScoreBoard scoreBoard = new ScoreBoard();
        final Team homeTeam = new Team("Spain");
        final Team awayTeam = new Team("England");

        //when & then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                scoreBoard.updateGameScore(homeTeam, awayTeam, 1, 2));
        assertEquals("Game between Argentina and Germany not found.", exception.getMessage());
    }

    @Test
    void updatingScoreShouldThrowExceptionWhenNegativeScoreIsProvided() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        final Team homeTeam = new Team("Spain");
        final Team awayTeam = new Team("England");
        scoreBoard.startGame(homeTeam, awayTeam);

        //when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateGameScore(homeTeam, awayTeam, -1, 2); // negative score
        });
        assertEquals("Score cannot be negative", exception.getMessage());
    }


    @Test
    void updatingScoreShouldThrowNotFoundException() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        final Team homeTeam = new Team("Scotland");
        final Team awayTeam = new Team("Poland");

        //when & then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                scoreBoard.updateGameScore(homeTeam, awayTeam, 2, 3));
        assertEquals("Game between Scotland and Poland not found.", exception.getMessage());
    }

    @Test
    void shouldReturnOrderedSummaryOfGames() {
        //given
        final ScoreBoard scoreBoard = new ScoreBoard();
        fillScoreBoardWithTestMatch(scoreBoard, "Mexico", "Canada", 0, 5);
        fillScoreBoardWithTestMatch(scoreBoard, "Spain", "Brazil", 10, 2);
        fillScoreBoardWithTestMatch(scoreBoard, "Germany", "France", 2, 2);
        fillScoreBoardWithTestMatch(scoreBoard, "Uruguay", "Italy", 6, 6);
        fillScoreBoardWithTestMatch(scoreBoard, "Argentina", "Australia", 3, 1);

        //when
        final List<String> summary = scoreBoard.getSummary();

        //then
        assertFalse(summary.isEmpty());
        assertEquals(5, summary.size());
        assertEquals("Uruguay 6 - Italy 6", summary.get(0));
        assertEquals("Spain 10 - Brazil 2", summary.get(1));
        assertEquals("Mexico 0 - Canada 5", summary.get(2));
        assertEquals("Argentina 3 - Australia 1", summary.get(3));
        assertEquals("Germany 2 - France 2", summary.get(4));
    }

    private void fillScoreBoardWithTestMatch(ScoreBoard scoreBoard, String homeTeamName, String awayTeamName, Integer homeScore, Integer awayScore) {
        final Team homeTeam = new Team(homeTeamName);
        final Team awayTeam = new Team(awayTeamName);

        scoreBoard.startGame(homeTeam, awayTeam);
        scoreBoard.updateGameScore(homeTeam, awayTeam, homeScore, awayScore);
    }
}
