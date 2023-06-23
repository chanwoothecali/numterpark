package com.numble.numterpark.venue.domain.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum VenueType {
    FIXED_SEAT("FIXED-SEAT"),
    STANDING("STANDING"),
    OUTDOOR("OUTDOOR");

    private String value;

    VenueType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static VenueType fromValue(String value) {
        for (VenueType type : VenueType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid VenueType: " + value);
    }
}
