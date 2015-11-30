package com.evgenii.waterfootprint.core;

import com.evgenii.waterfootprint.utils.WaterString;

import java.util.HashMap;
import java.util.Map;

public class SearchCache {
    static Map<String, String> cache = new HashMap<String, String>();

    public static String get(String text, Boolean ignoreDiacritic) {
        String value = cache.get(text);

        if (value == null) {
            value = text.toLowerCase();

            if (ignoreDiacritic) {
                value = WaterString.removeDiacritic(value);
            }

            clearIfTooLarge();

            cache.put(text, value);
        }

        return value;
    }

    static void clearIfTooLarge() {
        if (cache.size() > 10000) {
            cache.clear();
        }
    }

    static void clear() {
        cache.clear();
    }
}
