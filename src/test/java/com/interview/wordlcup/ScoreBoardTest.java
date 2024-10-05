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
    void startGameWithResultZeroZero() {
        //given
        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeamName = "Argentina";
        String awayTeamName = "Germany";
        Team homeTeam = new Team(homeTeamName);
        Team awayTeam = new Team(awayTeamName);
        //when
        List<Game> games = scoreBoard.startGame(homeTeam, awayTeam);

        //then
        assertFalse(games.isEmpty());
        assertEquals(1, games.size());

        Game game = games.stream().findFirst().get();
        assertEquals(homeTeamName, game.getHomeTeam().getName());
        assertEquals(awayTeamName, game.getAwayTeam().getName());
        assertEquals(0, game.getHomeScore().getScore());
        assertEquals(0, game.getAwayScore().getScore());
    }

    @Test
    void finishingGameShouldRemoveGameFromTheRunningGames() {
        //given
        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeamName = "Argentina";
        String awayTeamName = "Germany";
        Team homeTeam = new Team(homeTeamName);
        Team awayTeam = new Team(awayTeamName);
        //when
        scoreBoard.startGame(homeTeam, awayTeam);
        List<Game> games = scoreBoard.finishGame(homeTeam, awayTeam);

        //then
        assertTrue(games.isEmpty());
    }

    @Test
    void teamScoresShouldUpdateWithNewScore() {
        //given
        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeamName = "Spain";
        String awayTeamName = "England";
        Team homeTeam = new Team(homeTeamName);
        Team awayTeam = new Team(awayTeamName);
        //when
        scoreBoard.startGame(homeTeam, awayTeam);
        Game game = scoreBoard.updateGameScore(homeTeam, awayTeam, 2, 1);

        //then
        assertEquals(2, game.getHomeScore().getScore());
        assertEquals(1, game.getAwayScore().getScore());
    }

    @Test
    void updateTeamScoreShouldThrowNotFoundException() {
        //given
        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeamName = "Scotland";
        String awayTeamName = "Poland";
        Team homeTeam = new Team(homeTeamName);
        Team awayTeam = new Team(awayTeamName);

        //when & then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            scoreBoard.updateGameScore(homeTeam, awayTeam, 2, 3);
        });
        assertEquals("Game between Scotland and Poland not found.", exception.getMessage());
    }

    @Test
    void shouldReturnSummaryOfGames() {
        //given
        ScoreBoard scoreBoard = new ScoreBoard();
        fillScoreBoardWithTestMatches(scoreBoard, "Mexico", "Canada", 0, 5);
        fillScoreBoardWithTestMatches(scoreBoard, "Spain", "Brazil", 10, 2);
        fillScoreBoardWithTestMatches(scoreBoard, "Germany", "France", 2, 2);
        fillScoreBoardWithTestMatches(scoreBoard, "Uruguay", "Italy", 6, 6);
        fillScoreBoardWithTestMatches(scoreBoard, "Argentina", "Australia", 3, 1);

        //when
        List<String> summary = scoreBoard.getSummary();

        //then
        assertFalse(summary.isEmpty());
        assertEquals(5, summary.size());
        assertEquals("Uruguay 6 - Italy 6", summary.get(0));
        assertEquals("Spain 10 - Brazil 2", summary.get(1));
        assertEquals("Mexico 0 - Canada 5", summary.get(3));
        assertEquals("Argentina 3 - Australia 1", summary.get(5));
        assertEquals("Germany 2 - France 2", summary.get(2));
    }

    private void fillScoreBoardWithTestMatches(ScoreBoard scoreBoard, String homeTeamName, String awayTeamName, Integer homeScore, Integer awayScore ) {
        Team homeTeam = new Team(homeTeamName);
        Team awayTeam = new Team(awayTeamName);

        scoreBoard.startGame(homeTeam, awayTeam);
        scoreBoard.updateGameScore(homeTeam, awayTeam, homeScore, awayScore);
    }
}
