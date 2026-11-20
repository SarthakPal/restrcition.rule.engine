package com.tesco.restriction.rule;

import com.tesco.restriction.model.Item;
import com.tesco.restriction.model.RuleResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BulkBuyLimit: no single product may be bought in a quantity greater than the limit.
 * Quantities of the same product on several basket lines are added together.
 */
public final class BulkBuyLimitRule implements RestrictionRule {

    public static final String NAME = "BulkBuyLimit";

    private final int maxQuantity;

    public BulkBuyLimitRule(int maxQuantity) {
        if (maxQuantity <= 0) {
            throw new IllegalArgumentException("maxQuantity must be positive, was " + maxQuantity);
        }
        this.maxQuantity = maxQuantity;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public RuleResult check(List<Item> basket) {
        Map<String, Integer> quantityPerProduct = new HashMap<>();
        for (Item item : basket) {
            int total = quantityPerProduct.merge(item.getProductId(), item.getQuantity(), Integer::sum);
            if (total > maxQuantity) {
                return RuleResult.breached(NAME,
                        "Product " + item.getProductId() + " quantity " + total
                                + " exceeds limit " + maxQuantity);
            }
        }
        return RuleResult.met(NAME);
    }
}