package com.tesco.restriction.rule;

import com.tesco.restriction.model.Item;
import com.tesco.restriction.model.RuleResult;
import com.tesco.restriction.util.TextNormalizer;

import java.util.List;

/**
 * BulkBuyLimitCategory: the total quantity of all products in one category
 * may not be greater than the limit.
 */
public final class BulkBuyLimitCategoryRule implements RestrictionRule {

    public static final String NAME_PREFIX = "BulkBuyLimitCategory";

    private final String category;
    private final int maxQuantity;
    private final String name;

    public BulkBuyLimitCategoryRule(String category, int maxQuantity) {
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("category must not be blank");
        }
        if (maxQuantity <= 0) {
            throw new IllegalArgumentException("maxQuantity must be positive, was " + maxQuantity);
        }
        this.category = TextNormalizer.normalize(category);
        this.maxQuantity = maxQuantity;
        this.name = NAME_PREFIX + "[" + this.category + "]";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RuleResult check(List<Item> basket) {
        int total = 0;
        for (Item item : basket) {
            if (item.getCategory().equals(category)) {
                total += item.getQuantity();
                if (total > maxQuantity) {
                    return RuleResult.breached(name,
                            "Category " + category + " quantity " + total
                                    + " exceeds limit " + maxQuantity);
                }
            }
        }
        return RuleResult.met(name);
    }
}