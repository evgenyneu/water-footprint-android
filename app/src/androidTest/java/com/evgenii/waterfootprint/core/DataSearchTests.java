package com.evgenii.waterfootprint.core;

import android.test.AndroidTestCase;

public class DataSearchTests extends AndroidTestCase {

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
}
