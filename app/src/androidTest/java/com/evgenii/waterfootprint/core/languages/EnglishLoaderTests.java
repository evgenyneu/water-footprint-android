package com.evgenii.waterfootprint.core.languages;

import android.test.InstrumentationTestCase;

import com.evgenii.waterfootprint.core.CurrentLanguage;
import com.evgenii.waterfootprint.core.DataLoader;
import com.evgenii.waterfootprint.core.ProductModel;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReader;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import java.util.List;

public class EnglishLoaderTests extends InstrumentationTestCase {

    AssetsFileReaderInterface fileReader;

    protected void setUp() {
        CurrentLanguage.languageCodeCache = "en";

        fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());
    }

    protected void tearDown() {
        CurrentLanguage.languageCodeCache = null;
        DataLoader.productsCache = null;
    }

    public void testLoadCached() {
        List<ProductModel> result = DataLoader.loadCached(fileReader);

        assertEquals(234, result.size());

        // Product first
        // -----------

        ProductModel productFirst = result.get(0);
        assertEquals("Abaca fibre", productFirst.name);
        assertEquals("", productFirst.synonyms);
        assertEquals((int)22654, (int)productFirst.waterLitres);
    }

}
