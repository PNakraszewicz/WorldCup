package com.interview.wordlcup;

import com.interview.worldcup.Game;
import com.interview.worldcup.ScoreBoard;
import com.interview.worldcup.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreBoardTest {

    public static final String ENGLAND = "England";
    public static final String GERMANY = "Germany";
    public static final String FRANCE = "France";
    public static final int INITIAL_SCORE = 0;
    private ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    void startingGameShouldAddGameWithResultZeroZero() {
        //given
        final Team homeTeam = new Team(ENGLAND);
        final Team awayTeam = new Team(GERMANY);
        //when
        final List<Game> games = scoreBoard.startGame(homeTeam, awayTeam);

        //then
        assertFalse(games.isEmpty());
        assertEquals(1, games.size());

        final Game game = games.stream().findFirst().get();
        assertEquals(ENGLAND, game.getHomeTeam().getName());
        assertEquals(GERMANY, game.getAwayTeam().getName());
        assertEquals(INITIAL_SCORE, game.getHomeScore().getScore());
        assertEquals(INITIAL_SCORE, game.getAwayScore().getScore());
    }

    @Test
    void startingGameShouldThrowExceptionWhenTeamIsAlreadyPlayingOtherGame() {
        //given
        final Team awayTeam = new Team(GERMANY);
        final Team secondAwayTeam = new Team(FRANCE);
        final Team teamPlayingTwoMatchesSameTime = new Team(ENGLAND);

        //when
        scoreBoard.startGame(teamPlayingTwoMatchesSameTime, awayTeam);

        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.startGame(teamPlayingTwoMatchesSameTime, secondAwayTeam));
        assertEquals("Team England is already playing a different game.", exception.getMessage());
    }

    @Test
    void startingGameShouldThrowExceptionWhenHomeAndAwayTeamAreTheSame() {
        //given
        final Team homeTeam = new Team(ENGLAND);
        final Team awayTeam = new Team(ENGLAND);

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
        final String homeTeamName = "  ";
        final String awayTeamName = ENGLAND;
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
        final Team homeTeam = new Team(null);
        final Team awayTeam = new Team(ENGLAND);

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
        final Team homeTeam = new Team(ENGLAND);
        final Team awayTeam = new Team(GERMANY);
        //when
        scoreBoard.startGame(homeTeam, awayTeam);
        final List<Game> games = scoreBoard.finishGame(homeTeam, awayTeam);

        //then
        assertTrue(games.isEmpty());
    }

    @Test
    void finishingGameShouldThrowExceptionWhenMatchIsNotFound() {
        //given
        final Team homeTeam = new Team(ENGLAND);
        final Team awayTeam = new Team(GERMANY);
        final Team otherTeam = new Team("Romania");

        //when & then
        scoreBoard.startGame(homeTeam, awayTeam);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                scoreBoard.finishGame(otherTeam, awayTeam));
        assertEquals("Game between England and Germany not found.", exception.getMessage());
    }

    @Test
    void updatingScoreShouldSetNewScore() {
        //given
        final String homeTeamName = GERMANY;
        final String awayTeamName = ENGLAND;
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
        final Team homeTeam = new Team(GERMANY);
        final Team awayTeam = new Team(ENGLAND);

        //when & then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                scoreBoard.updateGameScore(homeTeam, awayTeam, 1, 2));
        assertEquals("Game between Germany and England not found.", exception.getMessage());
    }

    @Test
    void updatingScoreShouldThrowExceptionWhenNegativeScoreIsProvided() {
        //given
        final Team homeTeam = new Team(GERMANY);
        final Team awayTeam = new Team(ENGLAND);
        scoreBoard.startGame(homeTeam, awayTeam);

        //when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateGameScore(homeTeam, awayTeam, -1, 2); // negative score
        });
        assertEquals("Score cannot be negative", exception.getMessage());
    }

    @Test
    void shouldReturnOrderedSummaryOfGames() {
        //given
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
