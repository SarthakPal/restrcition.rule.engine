package com.tesco.restriction.rule;

import com.tesco.restriction.model.Item;
import com.tesco.restriction.model.RestrictionStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BulkBuyLimitRuleTest {

    private final BulkBuyLimitRule rule = new BulkBuyLimitRule(10);

    @Test
    void metWhenExactlyAtLimit() {
        assertEquals(RestrictionStatus.MET,
                rule.check(List.of(new Item("1", "chocolate", 10))).getStatus());
    }

    @Test
    void breachedWhenOverLimit() {
        assertEquals(RestrictionStatus.BREACHED,
                rule.check(List.of(new Item("1", "chocolate", 11))).getStatus());
    }

    @Test
    void sameProductOnSeveralLinesIsAddedUp() {
        List<Item> basket = List.of(new Item("1", "chocolate", 6), new Item("1", "chocolate", 5));
        assertEquals(RestrictionStatus.BREACHED, rule.check(basket).getStatus());
    }

    @Test
    void differentProductsAreCountedSeparately() {
        List<Item> basket = List.of(new Item("1", "chocolate", 10), new Item("2", "chocolate", 10));
        assertEquals(RestrictionStatus.MET, rule.check(basket).getStatus());
    }

    @Test
    void emptyBasketIsMet() {
        assertEquals(RestrictionStatus.MET, rule.check(List.of()).getStatus());
    }

    @Test
    void rejectsNonPositiveLimit() {
        assertThrows(IllegalArgumentException.class, () -> new BulkBuyLimitRule(0));
    }
}