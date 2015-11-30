package com.evgenii.waterfootprint.core;

import com.evgenii.waterfootprint.utils.WaterString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSearch {

    public static List<ProductModel> dataMatchingSearchText(List<ProductModel> models,
                                                            String searchText,
                                                            Boolean ignoreDiacritic) {

        List<String> searchWords = extractSearchWords(searchText);

        ArrayList<ProductModel> matchingModels = new ArrayList<ProductModel>();

        for(ProductModel model: models) {
            if (doesMatchSentence(model, searchWords, ignoreDiacritic)) {
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

    public static Boolean doesMatchSentence(ProductModel model, List<String> words,
                                            Boolean ignoreDiacritic) {

        if (words.size() == 0) { return true; }

        for (String word : words) {
            if (!doesMatchSingleWord(model, word, ignoreDiacritic)) {
                return false;
            }
        }

        return true;
    }

    public static Boolean doesMatchSingleWord(ProductModel model, String word, Boolean ignoreDiacritic) {
        word = SearchCache.get(word, ignoreDiacritic);
        String name = SearchCache.get(model.name, ignoreDiacritic);
        String synonyms = SearchCache.get(model.synonyms, ignoreDiacritic);


        if (name.contains(word)) {
            return true;
        }

        if (synonyms.contains(word)) {
            return true;
        }

        return false;
    }
}
