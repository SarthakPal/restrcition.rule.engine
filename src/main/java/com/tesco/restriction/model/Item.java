package com.tesco.restriction.model;

import com.tesco.restriction.util.TextNormalizer;

import java.util.Objects;

/**
 * One immutable line in a shopping basket.
 */
public final class Item {

    private final String productId;
    private final String category;
    private final int quantity;

    public Item(String productId, String category, int quantity) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("productId must not be blank");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("category must not be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive, was " + quantity);
        }
        this.productId = productId.trim();
        this.category = TextNormalizer.normalize(category);
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item other = (Item) o;
        return quantity == other.quantity
                && productId.equals(other.productId)
                && category.equals(other.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, category, quantity);
    }

    @Override
    public String toString() {
        return "Item{productId='" + productId + "', category='" + category + "', quantity=" + quantity + "}";
    }
}