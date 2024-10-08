package com.interview.worldcup.domain;

import lombok.Getter;

@Getter
public class Game {
    private final Team homeTeam;
    private final Team awayTeam;
    private final Score homeScore;
    private final Score awayScore;

    public Game(Team homeTeam, Team awayTeam) {
        validateTeams(homeTeam, awayTeam);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = new Score(0);
        this.awayScore = new Score(0);
    }

    public Game updateScore(final Integer homeScore, final Integer awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        this.homeScore.setValue(homeScore);
        this.awayScore.setValue(awayScore);
        return this;
    }

    private void validateTeams(Team homeTeam, Team awayTeam) {
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Team cannot play with itself");
        }
    }
}
