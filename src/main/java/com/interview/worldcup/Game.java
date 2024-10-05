package com.interview.worldcup;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Game {
    private final Team homeTeam;
    private final Team awayTeam;
    private final Score homeScore;
    private final Score awayScore;
    private final Instant creationTime;

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = new Score(0);
        this.awayScore = new Score(0);
        this.creationTime = Instant.now();
    }
    public Game updateScore(final Integer homeScore, final Integer awayScore) {
        this.homeScore.setScore(homeScore);
        this.awayScore.setScore(awayScore);
        return this;
    }
}
