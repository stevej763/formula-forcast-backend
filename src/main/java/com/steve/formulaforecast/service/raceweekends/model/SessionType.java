package com.steve.formulaforecast.service.raceweekends.model;

public enum SessionType {

    PRACTICE_1,

    PRACTICE_2,

    PRACTICE_3,

    SPRINT_QUALIFYING,

    SPRINT,

    QUALIFYING,

    RACE;

    public boolean isPractice() {
        return this == PRACTICE_1 || this == PRACTICE_2 || this == PRACTICE_3;
    }

    public int practiceSessionNumber() {
        return ordinal() + 1;
    }
}
