package com.evgenii.waterfootprint.core;

import android.test.AndroidTestCase;

public class DataSearchTests extends AndroidTestCase {

    // Match single word
    // ---------------------

    public void testDoesMatchSingleWord() {
        ProductModel model = new ProductModel();
        model.name = "Beef";
        model.synonyms = "Cow meat";
        model.waterLitres = 15415;

        assertTrue(DataSearch.doesMatchSingleWord(model, "bee"));
    }
}
