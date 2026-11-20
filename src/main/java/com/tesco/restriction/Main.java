package com.tesco.restriction;

import com.tesco.restriction.engine.RestrictionEngine;
import com.tesco.restriction.model.EvaluationResult;
import com.tesco.restriction.model.Item;
import com.tesco.restriction.rule.BulkBuyLimitCategoryRule;
import com.tesco.restriction.rule.BulkBuyLimitRule;

import java.util.List;

/**
 * Demo entry point using the example from the problem statement.
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        RestrictionEngine engine = new RestrictionEngine(List.of(
                new BulkBuyLimitRule(10),
                new BulkBuyLimitCategoryRule("Paracetamol", 5)
        ));

        List<Item> basket = List.of(
                new Item("1", "Paracetamol", 30),
                new Item("2", "analgesic", 3),
                new Item("3", "chocolate", 80),
                new Item("4", " Paracetamol", 2)
        );

        EvaluationResult result = engine.evaluate(basket);
        result.getRuleResults().forEach(r -> System.out.println("  " + r));
        System.out.println("Restriction status: " + result.getOverallStatus());
    }
}