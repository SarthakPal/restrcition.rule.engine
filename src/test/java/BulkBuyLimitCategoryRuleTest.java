package com.tesco.restriction.rule;

import com.tesco.restriction.model.Item;
import com.tesco.restriction.model.RestrictionStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BulkBuyLimitCategoryRuleTest {

    private final BulkBuyLimitCategoryRule rule = new BulkBuyLimitCategoryRule("Paracetamol", 5);

    @Test
    void metWhenCategoryTotalAtLimit() {
        List<Item> basket = List.of(new Item("1", "Paracetamol", 3), new Item("4", " paracetamol ", 2));
        assertEquals(RestrictionStatus.MET, rule.check(basket).getStatus());
    }

    @Test
    void breachedWhenCategoryTotalOverLimit() {
        List<Item> basket = List.of(new Item("1", "PARACETAMOL", 3), new Item("4", "paracetamol", 3));
        assertEquals(RestrictionStatus.BREACHED, rule.check(basket).getStatus());
    }

    @Test
    void otherCategoriesAreIgnored() {
        List<Item> basket = List.of(new Item("1", "chocolate", 50), new Item("2", "paracetamol", 1));
        assertEquals(RestrictionStatus.MET, rule.check(basket).getStatus());
    }

    @Test
    void rejectsBlankCategory() {
        assertThrows(IllegalArgumentException.class, () -> new BulkBuyLimitCategoryRule(" ", 5));
    }
}