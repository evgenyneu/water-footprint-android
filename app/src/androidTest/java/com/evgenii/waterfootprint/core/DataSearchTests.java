package com.evgenii.waterfootprint.core;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

public class DataSearchTests extends AndroidTestCase {

    // Data matching sentence
    // ---------------------

    public void testDataMatchingSearchText() {
        ProductModel model1 = new ProductModel();
        model1.name = "Beef";
        model1.synonyms = "Cow meat";

        ProductModel model2 = new ProductModel();
        model2.name = "Pork";
        model2.synonyms = "Pig meat";

        ProductModel model3 = new ProductModel();
        model3.name = "Asparagus";
        model3.synonyms = "";

        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        products.add(model1);
        products.add(model2);
        products.add(model3);

        List<ProductModel> result = DataSearch.dataMatchingSearchText(products, "meat", false,
                new SearchCache());

        assertEquals(2, result.size());
        assertEquals("Beef", result.get(0).name);
        assertEquals("Pork", result.get(1).name);
    }

    public void testDataMatchingSearchText_returnAllWhenEmptySearch() {
        ProductModel model1 = new ProductModel();
        model1.name = "Beef";
        model1.synonyms = "Cow meat";

        ProductModel model2 = new ProductModel();
        model2.name = "Pork";
        model2.synonyms = "Pig meat";

        ProductModel model3 = new ProductModel();
        model3.name = "Asparagus";
        model3.synonyms = "";

        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        products.add(model1);
        products.add(model2);
        products.add(model3);

        List<ProductModel> result = DataSearch.dataMatchingSearchText(products, "", false,
                new SearchCache());

        assertEquals(3, result.size());
    }

    public void testDataMatchingSearchText_matchByName() {
        ProductModel model1 = new ProductModel();
        model1.name = "Beef";
        model1.synonyms = "Cow meat";

        ProductModel model2 = new ProductModel();
        model2.name = "Pork";
        model2.synonyms = "Pig meat";

        ProductModel model3 = new ProductModel();
        model3.name = "Asparagus";
        model3.synonyms = "";

        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        products.add(model1);
        products.add(model2);
        products.add(model3);

        List<ProductModel> result = DataSearch.dataMatchingSearchText(products, "asp", false,
                new SearchCache());

        assertEquals(1, result.size());
        assertEquals("Asparagus", result.get(0).name);
    }

    public void testDataMatchingSearchText_noMatch() {
        ProductModel model1 = new ProductModel();
        model1.name = "Beef";
        model1.synonyms = "Cow meat";

        ProductModel model2 = new ProductModel();
        model2.name = "Pork";
        model2.synonyms = "Pig meat";

        ProductModel model3 = new ProductModel();
        model3.name = "Asparagus";
        model3.synonyms = "";

        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        products.add(model1);
        products.add(model2);
        products.add(model3);

        List<ProductModel> result = DataSearch.dataMatchingSearchText(products, "nomatch", false,
                new SearchCache());

        assertEquals(0, result.size());
    }

    public void testDataMatchingSearchText_noMatchOnlyOneWordMatches() {
        ProductModel model1 = new ProductModel();
        model1.name = "Beef";
        model1.synonyms = "Cow meat";

        ProductModel model2 = new ProductModel();
        model2.name = "Pork";
        model2.synonyms = "Pig meat";

        ProductModel model3 = new ProductModel();
        model3.name = "Asparagus";
        model3.synonyms = "";

        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        products.add(model1);
        products.add(model2);
        products.add(model3);

        List<ProductModel> result = DataSearch.dataMatchingSearchText(products, "chicken meat",
                false, new SearchCache());

        assertEquals(0, result.size());
    }

    public void testDataMatchingSearchText_matchDiacritic() {
        ProductModel model1 = new ProductModel();
        model1.name = "Перец свежий";
        model1.synonyms = "Чили, красный, чёрный";

        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        products.add(model1);

        List<ProductModel> result = DataSearch.dataMatchingSearchText(products, "черныи",
                false,
                new SearchCache());

        assertEquals(0, result.size());
    }

    public void testDataMatchingSearchText_ignoreDiacritic() {
        ProductModel model1 = new ProductModel();
        model1.name = "Перец свежий";
        model1.synonyms = "Чили, красный, чёрный";

        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        products.add(model1);

        List<ProductModel> result = DataSearch.dataMatchingSearchText(products, "черныи",
                true,
                new SearchCache());

        assertEquals(1, result.size());
    }

    // Extract search words
    // ---------------------
    
    public void testExtractSearchWords() {
        List<String> result = DataSearch.extractSearchWords("Beef");

        assertEquals(1, result.size());
        assertEquals("Beef", result.get(0));
    }

    public void testExtractSearchWords_ignoreSpaces() {
        List<String> result = DataSearch.extractSearchWords("   Beef   \t\r\n ");

        assertEquals(1, result.size());
        assertEquals("Beef", result.get(0));
    }

    public void testExtractSearchWords_multipleWords() {
        List<String> result = DataSearch.extractSearchWords("My lovely horse");

        assertEquals(3, result.size());
        assertEquals("My", result.get(0));
        assertEquals("lovely", result.get(1));
        assertEquals("horse", result.get(2));
    }

    // Match sentence
    // ---------------------

    public void testDoesMatchSentence() {
        ProductModel model = new ProductModel();
        model.name = "Beef";
        model.synonyms = "Cow meat";

        List<String> words = new ArrayList<String>();
        words.add("beef");
        words.add("meat");

        assertTrue(DataSearch.doesMatchSentence(model, words, false, new SearchCache()));
    }

    public void testDoesMatchSentence_oneWord() {
        ProductModel model = new ProductModel();
        model.name = "Beef";
        model.synonyms = "Cow meat";

        List<String> words = new ArrayList<String>();
        words.add("bee");

        assertTrue(DataSearch.doesMatchSentence(model, words, false, new SearchCache()));
    }

    public void testDoesMatchSentence_noMatch() {
        ProductModel model = new ProductModel();
        model.name = "Beef";
        model.synonyms = "Cow meat";

        List<String> words = new ArrayList<String>();
        words.add("checken");
        words.add("meat");

        assertFalse(DataSearch.doesMatchSentence(model, words, false, new SearchCache()));
    }

    public void testDoesMatchSentence_emptyWords() {
        ProductModel model = new ProductModel();
        model.name = "Beef";
        model.synonyms = "Cow meat";

        List<String> words = new ArrayList<String>();

        assertTrue(DataSearch.doesMatchSentence(model, words, false, new SearchCache()));
    }

    public void testDoesMatchSentence_ignoreDiacritic() {
        ProductModel model = new ProductModel();
        model.name = "Перец свежий";
        model.synonyms = "Чили, красный, чёрный";

        List<String> words = new ArrayList<String>();
        words.add("черный");

        assertTrue(DataSearch.doesMatchSentence(model, words, true, new SearchCache()));
    }

    public void testDoesMatchSentence_matchDiacritic() {
        ProductModel model = new ProductModel();
        model.name = "Перец свежий";
        model.synonyms = "Чили, красный, чёрный";

        List<String> words = new ArrayList<String>();
        words.add("черный");

        assertFalse(DataSearch.doesMatchSentence(model, words, false, new SearchCache()));
    }

    // Match single word
    // ---------------------

    public void testDoesMatchSingleWord() {
        ProductModel model = new ProductModel();
        model.name = "Beef";
        model.synonyms = "Cow meat";

        assertTrue(DataSearch.doesMatchSingleWord(model, "bee", false, new SearchCache()));
        assertTrue(DataSearch.doesMatchSingleWord(model, "Meat", false, new SearchCache()));
        assertTrue(DataSearch.doesMatchSingleWord(model, "", false, new SearchCache()));
        assertFalse(DataSearch.doesMatchSingleWord(model, "nothing", false, new SearchCache()));
    }

    public void testDoesMatchSingleWord_diacritic() {

        ProductModel model = new ProductModel();
        model.name = "Перец свежий";
        model.synonyms = "Чили, красный, чёрный";

        assertFalse(DataSearch.doesMatchSingleWord(model, "черный", false, new SearchCache()));
        assertTrue(DataSearch.doesMatchSingleWord(model, "черный", true, new SearchCache()));
    }

    public void testDoesMatchSingleWord_emptySynonym() {
        ProductModel model = new ProductModel();
        model.name = "Перец свежий";
        model.synonyms = "";

        assertTrue(DataSearch.doesMatchSingleWord(model, "", false, new SearchCache()));
        assertTrue(DataSearch.doesMatchSingleWord(model, "Перец", false, new SearchCache()));
        assertFalse(DataSearch.doesMatchSingleWord(model, "Nothing", false, new SearchCache()));
    }
}
