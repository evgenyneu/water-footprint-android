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

public class JapaneseLoaderTests extends InstrumentationTestCase {

    AssetsFileReaderInterface fileReader;

    protected void setUp() throws Exception {
        super.setUp();
        AppLocale.localeOverrideUsedInTests = Locale.JAPAN;

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

        assertEquals(231, result.size());

        // Product first
        // -----------

        ProductModel productFirst = result.get(0);
        assertEquals("アーティチョーク", productFirst.name);
        assertEquals("", productFirst.synonyms);
        assertEquals((int)818, (int)productFirst.waterLitres);

        // Product middle
        // -----------

        ProductModel productMiddle = result.get(105);
        assertEquals("砂糖（精製糖）", productMiddle.name);
        assertEquals("さとう,サトウ", productMiddle.synonyms);
        assertEquals((int)1782, (int)productMiddle.waterLitres);

        // Product last
        // -----------

        ProductModel productLast = result.get(result.size() - 1);
        assertEquals("綿（リント）", productLast.name);
        assertEquals("わた, めん,ワタ,メン,コットンリント,繰綿,もめん,モメン", productLast.synonyms);
        assertEquals((int)9113, (int)productLast.waterLitres);
    }

    public void testCheckDataTrimmed() {
        List<ProductModel> models = DataLoader.loadCached(fileReader);

        for(ProductModel model : models) {
            assertEquals(model.name, model.name.trim());
            assertEquals(model.synonyms, model.synonyms.trim());
        }
    }
}
