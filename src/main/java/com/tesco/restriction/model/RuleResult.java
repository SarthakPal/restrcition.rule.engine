package com.tesco.restriction.model;

import java.util.Objects;

/**
 * Result of running a single rule: which rule, its status, and a human-readable reason.
 */
public final class RuleResult {

    private final String ruleName;
    private final RestrictionStatus status;
    private final String message;

    private RuleResult(String ruleName, RestrictionStatus status, String message) {
        this.ruleName = Objects.requireNonNull(ruleName, "ruleName");
        this.status = Objects.requireNonNull(status, "status");
        this.message = Objects.requireNonNull(message, "message");
    }

    public static RuleResult met(String ruleName) {
        return new RuleResult(ruleName, RestrictionStatus.MET, "OK");
    }

    public static RuleResult breached(String ruleName, String reason) {
        return new RuleResult(ruleName, RestrictionStatus.BREACHED, reason);
    }

    public String getRuleName() {
        return ruleName;
    }

    public RestrictionStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isBreached() {
        return status == RestrictionStatus.BREACHED;
    }

    @Override
    public String toString() {
        return ruleName + " -> " + status + " (" + message + ")";
    }
}