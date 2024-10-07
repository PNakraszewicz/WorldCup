package com.interview.worldcup;

import lombok.Getter;

@Getter
public class Score {

    private Integer value;

    public Score(Integer value) {
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
