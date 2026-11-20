package com.tesco.restriction.model;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemTest {

    @Test
    void categoryIsNormalized() {
        assertEquals("paracetamol", new Item("1", "  PaRaCeTaMoL ", 1).getCategory());
    }

    @Test
    void rejectsZeroOrNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new Item("1", "chocolate", 0));
        assertThrows(IllegalArgumentException.class, () -> new Item("1", "chocolate", -1));
    }

    @Test
    void rejectsBlankProductIdOrCategory() {
        assertThrows(IllegalArgumentException.class, () -> new Item(" ", "chocolate", 1));
        assertThrows(IllegalArgumentException.class, () -> new Item("1", "", 1));
    }
}