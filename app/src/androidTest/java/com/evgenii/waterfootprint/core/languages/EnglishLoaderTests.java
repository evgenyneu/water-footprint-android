package com.evgenii.waterfootprint.core.languages;

import android.test.InstrumentationTestCase;

import com.evgenii.waterfootprint.core.AppLocale;
import com.evgenii.waterfootprint.core.CurrentLanguage;
import com.evgenii.waterfootprint.core.DataLoader;
import com.evgenii.waterfootprint.core.ProductModel;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReader;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import java.util.List;
import java.util.Locale;

public class EnglishLoaderTests extends InstrumentationTestCase {

    AssetsFileReaderInterface fileReader;

    protected void setUp() throws Exception {
        super.setUp();
        AppLocale.localeOverrideUsedInTests = Locale.ENGLISH;

        fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        AppLocale.localeOverrideUsedInTests = null;
        DataLoader.productsCache = null;
    }

    public void testLoadData() {
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

    public void testCheckDataTrimmed() {
        List<ProductModel> models = DataLoader.loadCached(fileReader);

        for(ProductModel model : models) {
            assertEquals(model.name, model.name.trim());
            assertEquals(model.synonyms, model.synonyms.trim());
        }
    }
}
