package com.evgenii.waterfootprint.core;

import com.evgenii.waterfootprint.utils.WaterString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSearch {

    public static List<ProductModel> dataMatchingSearchText(List<ProductModel> models,
                                                            String searchText) {

        List<String> searchWords = extractSearchWords(searchText);

        ArrayList<ProductModel> matchingModels = new ArrayList<ProductModel>();

        for(ProductModel model: models) {
            if (doesMatchSentence(model, searchWords)) {
                matchingModels.add(model);
            }
        }

        return matchingModels;
    }

    public static List<String> extractSearchWords(String searchText) {
        String[] words = searchText.split("\\s+");

        ArrayList<String> list = new ArrayList<String>();

        for (String word : words) {
            if (word.trim().length() > 0) {
                list.add(word);
            }
        }

        return list;
    }

    public static Boolean doesMatchSentence(ProductModel model, List<String> words) {
        if (words.size() == 0) { return false; }

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
