package com.interview.worldcup;

public record Team(String name) {

    public Team {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be empty");
        }
    }
}
