package com.evgenii.waterfootprint.core;

import com.evgenii.waterfootprint.utils.WaterString;

import java.util.List;

public class DataSearch {

    public static Boolean doesMatchSentence(ProductModel model, List<String> words) {
        for (String word : words) {
            if (!doesMatchSingleWord(model, word)) {
                return false;
            }
        }

        return true;
    }

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
