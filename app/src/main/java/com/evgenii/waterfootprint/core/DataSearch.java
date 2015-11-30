package com.evgenii.waterfootprint.core;

import com.evgenii.waterfootprint.utils.WaterString;

public class DataSearch {
    public static Boolean doesMatchSingleWord(ProductModel model, String word) {
        
        if (WaterString.containsIgnoreCaseAndDiacritic(model.name, word)) {
            return true;
        }

        if (WaterString.containsIgnoreCaseAndDiacritic(model.synonyms, word)) {
            return true;
        }

        return false;
    }
}
