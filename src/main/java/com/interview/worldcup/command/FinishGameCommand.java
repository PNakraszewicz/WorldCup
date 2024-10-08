package com.interview.worldcup.command;

import com.interview.worldcup.domain.Team;

public record FinishGameCommand(Team homeTeam, Team awayTeam) { }