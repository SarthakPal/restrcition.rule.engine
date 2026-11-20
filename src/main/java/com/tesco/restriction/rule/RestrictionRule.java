package com.tesco.restriction.rule;

import com.tesco.restriction.model.Item;
import com.tesco.restriction.model.RuleResult;

import java.util.List;

/**
 * Contract for every restriction rule (Strategy pattern).
 * Implementations must be stateless / immutable so one instance can be shared across threads.
 */
public interface RestrictionRule {

    /** Short, unique name used in reports and logs. */
    String getName();

    /**
     * Checks the basket against this rule.
     *
     * @param basket non-null list of non-null items (validated by the engine)
     * @return the result of the check, never null
     */
    RuleResult check(List<Item> basket);
}