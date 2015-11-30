package com.evgenii.waterfootprint.core;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

public class DataSearchTests extends AndroidTestCase {

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
