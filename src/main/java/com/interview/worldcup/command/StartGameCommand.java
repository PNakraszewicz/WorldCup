package com.interview.worldcup.command;

import com.interview.worldcup.domain.Team;

public record StartGameCommand(Team homeTeam, Team awayTeam) { }