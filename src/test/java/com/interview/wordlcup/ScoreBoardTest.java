package com.interview.wordlcup;

import com.interview.worldcup.Game;
import com.interview.worldcup.ScoreBoard;
import com.interview.worldcup.Team;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

}
