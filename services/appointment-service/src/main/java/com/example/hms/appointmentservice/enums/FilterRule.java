package com.example.hms.appointmentservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FilterRule {
    EQUALS("eq"),
    NOT_EQUALS("neq"),
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUALS("gte"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUALS("lte"),
    LIKE("like"),
    NOT_LIKE("nlike"),
    IN("in"),
    NOT_IN("nin"),
    IS_NULL("isnull"),
    IS_NOT_NULL("isnotnull");

    private final String value;

    public static FilterRule fromValue(String value) {
        for (FilterRule rule : FilterRule.values()) {
            if (rule.getValue().equalsIgnoreCase(value)) {
                return rule;
            }
        }
        throw new IllegalArgumentException("Unknown filter rule: " + value);
    }
}
