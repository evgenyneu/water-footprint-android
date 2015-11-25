package com.evgenii.waterfootprint.core;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReader;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import junit.framework.Assert;

import java.util.List;

public class DataLoaderTests extends InstrumentationTestCase {

    protected void tearDown() {
        CurrentLanguage.languageCodeCache = null;
    }

    public void testLoadTextForLanguage() {
        AssetsFileReaderInterface fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());

        String result = DataLoader.loadTextForLanguage("en", fileReader);

        assert(result.startsWith("Abaca fibre"));
    }

    public void testLoadDataForLanguage() {
        AssetsFileReaderInterface fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());

        List<ProductModel> result = DataLoader.loadForLanguage("en", fileReader);

        assertEquals(0, result.size());
    }

    public void testLoadDataFromText() {
        List<ProductModel> result = DataLoader.loadFromText("one\t11\t111\r\ntwo\t22\t222");
    }

    // Load single product
    // ---------------------------

    public void testLoadSingleProductFromText() {
        ProductModel result = DataLoader.loadSingleProductFromText("one\t11\t111");

        assertEquals("one", result.name);
        assertEquals("11", result.synonyms);
        assertEquals((int) 111, (int) result.waterLitres);
    }

    public void testLoadSingleProductFromText_incomplete() {
        try {
            ProductModel result = DataLoader.loadSingleProductFromText("imcomplete");
            Assert.fail("Should have thrown RuntimeException exception");
        }
        catch (java.lang.RuntimeException e) {
            assertEquals("Incomplete product data: imcomplete", e.getMessage());
            // success
        }
    }

    public void testLoadSingleProductFromText_incorrectWaterFootprint() {
        try {
            ProductModel result = DataLoader.loadSingleProductFromText("one\t11\tincorrect");
            Assert.fail("Should have thrown RuntimeException exception");
        }
        catch (java.lang.RuntimeException e) {
            assertEquals("Error reading water footprint value: one\t11\tincorrect", e.getMessage());
            // success
        }
    }
}