package com.tesco.restriction.engine;

import com.tesco.restriction.model.EvaluationResult;
import com.tesco.restriction.model.Item;
import com.tesco.restriction.model.RuleResult;
import com.tesco.restriction.rule.RestrictionRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs every configured restriction rule against a basket.
 * Immutable and thread-safe: build once at startup, share across all requests.
 */
public final class RestrictionEngine {

    private final List<RestrictionRule> rules;

    public RestrictionEngine(List<RestrictionRule> rules) {
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("At least one rule is required");
        }
        // Note: List.of(...).contains(null) throws NullPointerException, so loop instead
        for (RestrictionRule rule : rules) {
            if (rule == null) {
                throw new IllegalArgumentException("rules must not contain null");
            }
        }
        this.rules = List.copyOf(rules);
    }

    /**
     * Checks the basket against all rules. All rules are run (not stopped at the first breach)
     * so the caller can show the customer every reason the basket was rejected.
     */
    public EvaluationResult evaluate(List<Item> basket) {
        validate(basket);
        List<RuleResult> results = new ArrayList<>(rules.size());
        for (RestrictionRule rule : rules) {
            results.add(rule.check(basket));
        }
        return new EvaluationResult(results);
    }

    public List<RestrictionRule> getRules() {
        return rules;
    }

    private static void validate(List<Item> basket) {
        if (basket == null) {
            throw new IllegalArgumentException("basket must not be null");
        }
        for (Item item : basket) {
            if (item == null) {
                throw new IllegalArgumentException("basket must not contain null items");
            }
        }
    }
}