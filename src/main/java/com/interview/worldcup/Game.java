package com.interview.worldcup;

public class Game {
    private final Team homeTeam;
    private final Team awayTeam;
    private Score homeScore;
    private Score awayScore;

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = new Score(0);
        this.awayScore = new Score(0);
    }
    public Game updateScore(final Integer homeScore, final Integer awayScore) {
        this.homeScore.setScore(homeScore);
        this.awayScore.setScore(awayScore);
        return this;
    }
    public Score getAwayScore() {
        return awayScore;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Score getHomeScore() {
        return homeScore;
    }

}
