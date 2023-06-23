package com.numble.numterpark.venue.domain.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SeatType {
    VIP("VIP"),
    NORMAL("일반");

    private String value;

    SeatType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SeatType fromValue(String value) {
        for (SeatType type : SeatType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid SeatType: " + value);
    }
}
