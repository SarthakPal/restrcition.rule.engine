package com.tesco.restriction.util;

import java.util.Locale;

/**
 * Normalizes text so that values like " Paracetamol" and "paracetamol" compare as equal.
 */
public final class TextNormalizer {

    private TextNormalizer() {
        // Utility class: no instances
    }

    public static String normalize(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text must not be null");
        }
        // Locale.ROOT avoids locale bugs (e.g. Turkish 'I' lower-casing)
        return text.trim().toLowerCase(Locale.ROOT);
    }
}