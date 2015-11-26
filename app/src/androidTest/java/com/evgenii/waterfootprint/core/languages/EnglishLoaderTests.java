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

        // Product middle
        // -----------

        ProductModel productMiddle = result.get(105);
        assertEquals("Lamb", productMiddle.name);
        assertEquals("Sheep meat, hogget, mutton", productMiddle.synonyms);
        assertEquals((int)10412, (int)productMiddle.waterLitres);

        // Product last
        // -----------

        ProductModel productLast = result.get(result.size() - 1);
        assertEquals("Yams", productLast.name);
        assertEquals("", productLast.synonyms);
        assertEquals((int)343, (int)productLast.waterLitres);
    }

}
