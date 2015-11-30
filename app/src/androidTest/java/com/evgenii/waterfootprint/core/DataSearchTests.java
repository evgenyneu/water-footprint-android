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

        List<ProductModel> result = DataSearch.dataMatchingSearchText(products, "meat");

        assertEquals(2, result.size());
        assertEquals("Beef", result.get(0).name);
        assertEquals("Pork", result.get(1).name);
    }

    public void testDataMatchingSearchText_emptySearch() {
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

        List<ProductModel> result = DataSearch.dataMatchingSearchText(products, "");

        assertEquals(0, result.size());
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

        List<ProductModel> result = DataSearch.dataMatchingSearchText(products, "asp");

        assertEquals(1, result.size());
        assertEquals("Asparagus", result.get(0).name);
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

        assertTrue(DataSearch.doesMatchSentence(model, words));
    }

    public void testDoesMatchSentence_oneWord() {
        ProductModel model = new ProductModel();
        model.name = "Beef";
        model.synonyms = "Cow meat";

        List<String> words = new ArrayList<String>();
        words.add("bee");

        assertTrue(DataSearch.doesMatchSentence(model, words));
    }

    public void testDoesMatchSentence_noMatch() {
        ProductModel model = new ProductModel();
        model.name = "Beef";
        model.synonyms = "Cow meat";

        List<String> words = new ArrayList<String>();
        words.add("checken");
        words.add("meat");

        assertFalse(DataSearch.doesMatchSentence(model, words));
    }

    public void testDoesMatchSentence_emptyWords() {
        ProductModel model = new ProductModel();
        model.name = "Beef";
        model.synonyms = "Cow meat";

        List<String> words = new ArrayList<String>();

        assertFalse(DataSearch.doesMatchSentence(model, words));
    }

    // Match single word
    // ---------------------

    public void testDoesMatchSingleWord() {
        ProductModel model = new ProductModel();
        model.name = "Beef";
        model.synonyms = "Cow meat";

        assertTrue(DataSearch.doesMatchSingleWord(model, "bee"));
        assertTrue(DataSearch.doesMatchSingleWord(model, "Meat"));
        assertTrue(DataSearch.doesMatchSingleWord(model, ""));
        assertFalse(DataSearch.doesMatchSingleWord(model, "nothing"));
    }

    public void testDoesMatchSingleWord_diacritic() {
        ProductModel model = new ProductModel();
        model.name = "Перец свежий";
        model.synonyms = "Чили, красный, чёрный";

        assertTrue(DataSearch.doesMatchSingleWord(model, "черный"));
    }

    public void testDoesMatchSingleWord_emptySynonym() {
        ProductModel model = new ProductModel();
        model.name = "Перец свежий";
        model.synonyms = "";

        assertTrue(DataSearch.doesMatchSingleWord(model, ""));
        assertTrue(DataSearch.doesMatchSingleWord(model, "Перец"));
        assertFalse(DataSearch.doesMatchSingleWord(model, "Nothing"));
    }
}
