package com.evgenii.waterfootprint.core;

import com.evgenii.waterfootprint.utils.WaterString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSearch {

    public static List<ProductModel> dataMatchingSearchText(List<ProductModel> models,
                                                            String searchText,
                                                            Boolean ignoreDiacritic,
                                                            SearchCache searchCache) {

        List<String> searchWords = extractSearchWords(searchText);

        ArrayList<ProductModel> matchingModels = new ArrayList<ProductModel>();

        for(ProductModel model: models) {
            if (doesMatchSentence(model, searchWords, ignoreDiacritic, searchCache)) {
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
                                            Boolean ignoreDiacritic, SearchCache searchCache) {

        if (words.size() == 0) { return true; }

        for (String word : words) {
            if (!doesMatchSingleWord(model, word, ignoreDiacritic, searchCache)) {
                return false;
            }
        }

        return true;
    }

    public static Boolean doesMatchSingleWord(ProductModel model, String word,
                                              Boolean ignoreDiacritic, SearchCache searchCache) {
        word = searchCache.get(word, ignoreDiacritic);
        String name = searchCache.get(model.name, ignoreDiacritic);
        String synonyms = searchCache.get(model.synonyms, ignoreDiacritic);


        if (name.contains(word)) {
            return true;
        }

        if (synonyms.contains(word)) {
            return true;
        }

        return false;
    }
}
