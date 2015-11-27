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

public class ChineseLoaderTests extends InstrumentationTestCase {

    AssetsFileReaderInterface fileReader;

    protected void setUp() {
        AppLocale.localeOverrideUsedInTests = Locale.SIMPLIFIED_CHINESE;

        fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());
    }

    protected void tearDown() {
        AppLocale.localeOverrideUsedInTests = null;
        DataLoader.productsCache = null;
    }

    public void testLoadData() {
        List<ProductModel> result = DataLoader.loadCached(fileReader);

        assertEquals(233, result.size());

        // Product first
        // -----------

        ProductModel productFirst = result.get(0);
        assertEquals("八角", productFirst.name);
        assertEquals("茴香，茴芹，大茴香", productFirst.synonyms);
        assertEquals((int)8280, (int)productFirst.waterLitres);

        // Product middle
        // -----------

        ProductModel productMiddle = result.get(104);
        assertEquals("萝卜", productMiddle.name);
        assertEquals("白萝卜，红萝卜，罗服，荠根，萝欠，芦菔，萝白", productMiddle.synonyms);
        assertEquals((int)195, (int)productMiddle.waterLitres);

        // Product last
        // -----------

        ProductModel productLast = result.get(result.size() - 1);
        assertEquals("棕榈油", productLast.name);
        assertEquals("", productLast.synonyms);
        assertEquals((int)4971, (int)productLast.waterLitres);
    }

    public void testCheckDataTrimmed() {
        List<ProductModel> models = DataLoader.loadCached(fileReader);

        for(ProductModel model : models) {
            assertEquals(model.name, model.name.trim());
            assertEquals(model.synonyms, model.synonyms.trim());
        }
    }
}
