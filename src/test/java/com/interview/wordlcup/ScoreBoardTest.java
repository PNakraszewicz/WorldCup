package com.interview.wordlcup;

import com.interview.worldcup.FinishGameCommand;
import com.interview.worldcup.Game;
import com.interview.worldcup.ScoreBoard;
import com.interview.worldcup.StartGameCommand;
import com.interview.worldcup.Team;
import com.interview.worldcup.UpdateGameCommand;
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
        final StartGameCommand command = new StartGameCommand(new Team(ENGLAND), new Team(GERMANY));

        //when
        final List<Game> games = scoreBoard.startGame(command);

        //then
        assertFalse(games.isEmpty());
        assertEquals(1, games.size());

        final Game game = games.stream().findFirst().get();
        assertEquals(ENGLAND, game.getHomeTeam().name());
        assertEquals(GERMANY, game.getAwayTeam().name());
        assertEquals(INITIAL_SCORE, game.getHomeScore().getValue());
        assertEquals(INITIAL_SCORE, game.getAwayScore().getValue());
    }

    @Test
    void startingGameShouldThrowExceptionWhenTeamIsAlreadyPlayingOtherGame() {
        //given
        final StartGameCommand firstCommand = new StartGameCommand(new Team(ENGLAND), new Team(GERMANY));
        final StartGameCommand secondCommand = new StartGameCommand(new Team(ENGLAND), new Team(FRANCE));

        //when
        scoreBoard.startGame(firstCommand);

        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.startGame(secondCommand));
        assertEquals("One or both teams are already playing in another game", exception.getMessage());
    }

    @Test
    void startingGameShouldThrowExceptionWhenHomeAndAwayTeamAreTheSame() {
        //given
        final StartGameCommand command = new StartGameCommand(new Team(ENGLAND), new Team(ENGLAND));

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.startGame(command));

        //then
        assertEquals("Team cannot play with itself", exception.getMessage());
    }

    @Test
    void finishingGameShouldRemoveGameFromTheRunningGames() {
        //given
        final StartGameCommand startCommand = new StartGameCommand(new Team(ENGLAND), new Team(GERMANY));
        final FinishGameCommand finishCommand = new FinishGameCommand(new Team(ENGLAND), new Team(GERMANY));

        //when
        scoreBoard.startGame(startCommand);
        final List<Game> games = scoreBoard.finishGame(finishCommand);

        //then
        assertTrue(games.isEmpty());
    }

    @Test
    void finishingGameShouldThrowExceptionWhenMatchIsNotFound() {
        //given
        final StartGameCommand startCommand = new StartGameCommand(new Team(ENGLAND), new Team(GERMANY));
        final FinishGameCommand finishCommand = new FinishGameCommand(new Team("Romania"), new Team(GERMANY));

        //when & then
        scoreBoard.startGame(startCommand);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                scoreBoard.finishGame(finishCommand));
        assertEquals("Game between Romania and Germany not found.", exception.getMessage());
    }

    @Test
    void updatingScoreShouldSetNewScore() {
        //given
        final StartGameCommand startCommand = new StartGameCommand(new Team(GERMANY), new Team(ENGLAND));
        final UpdateGameCommand updateCommand = new UpdateGameCommand(new Team(GERMANY), new Team(ENGLAND), 2, 1);

        //when
        scoreBoard.startGame(startCommand);
        final Game game = scoreBoard.updateGameScore(updateCommand);

        //then
        assertEquals(2, game.getHomeScore().getValue());
        assertEquals(1, game.getAwayScore().getValue());
        assertEquals(GERMANY, game.getHomeTeam().name());
        assertEquals(ENGLAND, game.getAwayTeam().name());
    }

    @Test
    void updatingScoreShouldThrowExceptionWhenMatchIsNotOnTheBoard() {
        //given
        final UpdateGameCommand updateCommand = new UpdateGameCommand(new Team(GERMANY), new Team(ENGLAND), 1, 2);

        //when & then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                scoreBoard.updateGameScore(updateCommand));
        assertEquals("Game between Germany and England not found.", exception.getMessage());
    }

    @Test
    void updatingScoreShouldThrowExceptionWhenNegativeScoreIsProvided() {
        //given
        final StartGameCommand startCommand = new StartGameCommand(new Team(GERMANY), new Team(ENGLAND));
        final UpdateGameCommand updateCommand = new UpdateGameCommand(new Team(GERMANY), new Team(ENGLAND), -1, 2);

        //when
        scoreBoard.startGame(startCommand);

        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.updateGameScore(updateCommand));
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
        final StartGameCommand startCommand = new StartGameCommand(new Team(homeTeamName), new Team(awayTeamName));
        final UpdateGameCommand updateCommand = new UpdateGameCommand(new Team(homeTeamName), new Team(awayTeamName), homeScore, awayScore);

        scoreBoard.startGame(startCommand);
        scoreBoard.updateGameScore(updateCommand);
    }
}
