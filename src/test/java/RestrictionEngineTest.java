package com.tesco.restriction.engine;

import com.tesco.restriction.model.EvaluationResult;
import com.tesco.restriction.model.Item;
import com.tesco.restriction.model.RestrictionStatus;
import com.tesco.restriction.rule.BulkBuyLimitCategoryRule;
import com.tesco.restriction.rule.BulkBuyLimitRule;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestrictionEngineTest {

    private final RestrictionEngine engine = new RestrictionEngine(List.of(
            new BulkBuyLimitRule(10),
            new BulkBuyLimitCategoryRule("Paracetamol", 5)
    ));

    private final List<Item> exampleBasket = List.of(
            new Item("1", "Paracetamol", 3),
            new Item("2", "analgesic", 3),
            new Item("3", "chocolate", 8),
            new Item("4", " Paracetamol", 2)
    );

    @Test
    void exampleFromProblemStatementIsMet() {
        EvaluationResult result = engine.evaluate(exampleBasket);
        assertEquals(RestrictionStatus.MET, result.getOverallStatus());
        assertEquals(0, result.getBreachedRules().size());
    }

    @Test
    void oneBreachedRuleMakesOverallBreached() {
        List<Item> basket = new ArrayList<>(exampleBasket);
        basket.add(new Item("5", "paracetamol", 1));

        EvaluationResult result = engine.evaluate(basket);

        assertEquals(RestrictionStatus.BREACHED, result.getOverallStatus());
        assertEquals(1, result.getBreachedRules().size());
        assertEquals("BulkBuyLimitCategory[paracetamol]", result.getBreachedRules().get(0).getRuleName());
    }

    @Test
    void reportsEveryBreachedRule() {
        List<Item> basket = List.of(new Item("1", "paracetamol", 11));
        assertEquals(2, engine.evaluate(basket).getBreachedRules().size());
    }

    @Test
    void rejectsNullBasket() {
        assertThrows(IllegalArgumentException.class, () -> engine.evaluate(null));
    }

    @Test
    void rejectsNullItemInBasket() {
        assertThrows(IllegalArgumentException.class,
                () -> engine.evaluate(Arrays.asList(new Item("1", "chocolate", 1), null)));
    }

    @Test
    void rejectsEmptyRuleList() {
        assertThrows(IllegalArgumentException.class, () -> new RestrictionEngine(List.of()));
    }
}