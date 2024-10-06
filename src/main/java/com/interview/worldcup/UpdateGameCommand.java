package com.interview.worldcup;

public record UpdateGameCommand(Team homeTeam, Team awayTeam, int homeScore, int awayScore) { }
