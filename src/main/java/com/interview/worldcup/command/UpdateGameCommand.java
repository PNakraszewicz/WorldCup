package com.interview.worldcup.command;

import com.interview.worldcup.domain.Team;

public record UpdateGameCommand(Team homeTeam, Team awayTeam, int homeScore, int awayScore) { }
