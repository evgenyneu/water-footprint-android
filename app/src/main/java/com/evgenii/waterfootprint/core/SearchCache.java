package com.evgenii.waterfootprint.core;

import com.evgenii.waterfootprint.utils.WaterString;

import java.util.HashMap;
import java.util.Map;

public class SearchCache {
    Map<String, String> cache = new HashMap<String, String>();

    public String get(String text, Boolean ignoreDiacritic) {
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

    void clearIfTooLarge() {
        if (cache.size() > 1000) {
            clear();
        }
    }

    public void clear() {
        cache.clear();
    }
}
