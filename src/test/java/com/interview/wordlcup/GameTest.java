package com.interview.wordlcup;

import com.interview.worldcup.Game;
import com.interview.worldcup.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    public static final String GERMANY = "Germany";
    public static final String ENGLAND = "England";

    @Test
    void shouldCreateGameWithValidTeamsAndInitialScoreZero() {
        // given
        Team homeTeam = new Team(ENGLAND);
        Team awayTeam = new Team(GERMANY);

        // when
        Game game = new Game(homeTeam, awayTeam);

        // then
        assertEquals(homeTeam, game.getHomeTeam());
        assertEquals(awayTeam, game.getAwayTeam());
        assertEquals(0, game.getHomeScore().getValue());
        assertEquals(0, game.getAwayScore().getValue());
    }

    @Test
    void shouldUpdateScoreWithValidValues() {
        // given
        Team homeTeam = new Team(ENGLAND);
        Team awayTeam = new Team(GERMANY);
        Game game = new Game(homeTeam, awayTeam);

        // when
        game.updateScore(2, 1);

        // then
        assertEquals(2, game.getHomeScore().getValue());
        assertEquals(1, game.getAwayScore().getValue());
    }
}