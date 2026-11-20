package com.tesco.restriction.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Overall result of checking a basket against all rules.
 * Overall status is BREACHED if at least one rule is breached, otherwise MET.
 */
public final class EvaluationResult {

    private final RestrictionStatus overallStatus;
    private final List<RuleResult> ruleResults;

    public EvaluationResult(List<RuleResult> ruleResults) {
        if (ruleResults == null) {
            throw new IllegalArgumentException("ruleResults must not be null");
        }
        this.ruleResults = List.copyOf(ruleResults);
        this.overallStatus = this.ruleResults.stream().anyMatch(RuleResult::isBreached)
                ? RestrictionStatus.BREACHED
                : RestrictionStatus.MET;
    }

    public RestrictionStatus getOverallStatus() {
        return overallStatus;
    }

    public List<RuleResult> getRuleResults() {
        return ruleResults;
    }

    public List<RuleResult> getBreachedRules() {
        return ruleResults.stream()
                .filter(RuleResult::isBreached)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String toString() {
        return "EvaluationResult{overallStatus=" + overallStatus + ", ruleResults=" + ruleResults + "}";
    }
}