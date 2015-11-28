package com.evgenii.waterfootprint.core;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReader;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataLoaderTests extends InstrumentationTestCase {

    protected void tearDown() throws Exception {
        super.tearDown();
        AppLocale.localeOverrideUsedInTests = null;
        DataLoader.productsCache = null;
    }

    public void testLoadCached() {
        AppLocale.localeOverrideUsedInTests = Locale.SIMPLIFIED_CHINESE;

        AssetsFileReaderInterface fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());

        List<ProductModel> result = DataLoader.loadCached(fileReader);

        assertEquals(233, result.size());

        // Product first
        // -----------

        ProductModel productFirst = result.get(0);
        assertEquals("八角", productFirst.name);
        assertEquals("茴香，茴芹，大茴香", productFirst.synonyms);
        assertEquals((int)8280, (int)productFirst.waterLitres);
    }

    public void testLoadCached_fromCache() {
        AppLocale.localeOverrideUsedInTests = Locale.SIMPLIFIED_CHINESE;
        ArrayList<ProductModel> products = new ArrayList<ProductModel>();

        ProductModel productOne = new ProductModel();
        productOne.name = "Test name";
        productOne.synonyms = "Test synonym";
        productOne.waterLitres = 5345;

        products.add(productOne);

        DataLoader.productsCache = products;

        AssetsFileReaderInterface fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());

        List<ProductModel> result = DataLoader.loadCached(fileReader);

        assertEquals(1, result.size());

        // Product first
        // -----------

        ProductModel productFirst = result.get(0);
        assertEquals("Test name", productFirst.name);
        assertEquals("Test synonym", productFirst.synonyms);
        assertEquals((int)5345, (int)productFirst.waterLitres);
    }

    public void testLoad() {
        AppLocale.localeOverrideUsedInTests = Locale.JAPAN;

        AssetsFileReaderInterface fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());

        List<ProductModel> result = DataLoader.load(fileReader);

        assertEquals(231, result.size());

        // Product first
        // -----------

        ProductModel productFirst = result.get(0);
        assertEquals("アーティチョーク", productFirst.name);
        assertEquals("", productFirst.synonyms);
        assertEquals((int)818, (int)productFirst.waterLitres);
    }

    public void testLoadDataForLanguage() {
        AssetsFileReaderInterface fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());

        List<ProductModel> result = DataLoader.loadForLanguage("en", fileReader);

        assertEquals(234, result.size());

        // Product first
        // -----------

        ProductModel productFirst = result.get(0);
        assertEquals("Abaca fibre", productFirst.name);
        assertEquals("", productFirst.synonyms);
        assertEquals((int)22654, (int)productFirst.waterLitres);
    }

    public void testLoadTextForLanguage() {
        AssetsFileReaderInterface fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());

        String result = DataLoader.loadTextForLanguage("en", fileReader);

        assert(result.startsWith("Abaca fibre"));
    }

    public void testLoadDataFromText() {
        List<ProductModel> result = DataLoader.loadFromText("one\t11\t111\r\ntwo\t22\t222");

        assertEquals(2, result.size());

        // Product one
        // -----------

        ProductModel productOne = result.get(0);
        assertEquals("one", productOne.name);
        assertEquals("11", productOne.synonyms);
        assertEquals((int)111, (int)productOne.waterLitres);

        // Product two
        // -----------

        ProductModel productTwo = result.get(1);
        assertEquals("two", productTwo.name);
        assertEquals("22", productTwo.synonyms);
        assertEquals((int)222, (int)productTwo.waterLitres);
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