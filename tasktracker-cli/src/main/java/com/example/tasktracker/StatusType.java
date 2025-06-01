package com.example.tasktracker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum StatusType {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String label;

    StatusType(String label) {
        this.label = label;
    }

    // serializing enum to JSON
    @JsonValue
    public String getLabel() {
        return label;
    }

    // deserializing JSON to enum
    @JsonCreator
    public static StatusType fromLabel(String label) {
        return Stream.of(StatusType.values())
                     .filter(status -> status.label.equalsIgnoreCase(label))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Invalid status: " + label));
    }

    // Override toString() to return the label when printed
    @Override
    public String toString() {
        return label;
    }
}
