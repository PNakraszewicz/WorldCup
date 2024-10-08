package com.interview.worldcup.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Score {

    private Integer value;

    public Score(Integer value) {
        this.value = value;
    }
}
