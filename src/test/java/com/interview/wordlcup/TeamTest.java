package com.interview.wordlcup;

import com.interview.worldcup.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TeamTest {

    @Test
    void shouldCreateTeamWithValidName() {
        // given
        String validName = "England";
        // when
        Team team = new Team(validName);
        // then
        assertEquals(validName, team.name());
    }

    @Test
    void shouldThrowExceptionWhenTeamNameIsNull() {
        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Team(null));

        assertEquals("Team name cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTeamNameIsEmpty() {
        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Team("  "));

        assertEquals("Team name cannot be empty", exception.getMessage());
    }
}
