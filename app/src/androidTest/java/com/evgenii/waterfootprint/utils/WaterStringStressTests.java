package com.evgenii.waterfootprint.utils;

import android.test.InstrumentationTestCase;

import com.evgenii.waterfootprint.core.AppLocale;
import com.evgenii.waterfootprint.core.DataLoader;
import com.evgenii.waterfootprint.core.ProductModel;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReader;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import java.util.List;
import java.util.Locale;


public class WaterStringStressTests extends InstrumentationTestCase {

    AssetsFileReaderInterface fileReader;

    protected void setUp() throws Exception {
        super.setUp();

        fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        AppLocale.localeOverrideUsedInTests = null;
    }

    public void testChinese() {
        AppLocale.localeOverrideUsedInTests = Locale.SIMPLIFIED_CHINESE;

        List<ProductModel> models = DataLoader.load(fileReader);

        assertEquals(233, models.size());

        for(ProductModel model : models) {
            Boolean result = WaterString.containsIgnoreCaseAndDiacritic(model.name + "suffix",
                    model.name );

            assertTrue(result);

            Boolean resultFalse = WaterString.containsIgnoreCaseAndDiacritic(model.name,
                    model.name + "suffix");

            assertFalse(resultFalse);
        }
    }

    public void testJapanese() {
        AppLocale.localeOverrideUsedInTests = Locale.JAPAN;

        List<ProductModel> models = DataLoader.load(fileReader);

        assertEquals(231, models.size());

        for(ProductModel model : models) {
            Boolean result = WaterString.containsIgnoreCaseAndDiacritic(model.name + "suffix",
                    model.name );

            assertTrue(result);

            Boolean resultFalse = WaterString.containsIgnoreCaseAndDiacritic(model.name,
                    model.name + "suffix");

            assertFalse(resultFalse);
        }
    }

    public void testRussian() {
        AppLocale.localeOverrideUsedInTests = new Locale("ru");

        List<ProductModel> models = DataLoader.load(fileReader);

        assertEquals(230, models.size());

        for(ProductModel model : models) {
            Boolean result = WaterString.containsIgnoreCaseAndDiacritic(model.name + "suffix",
                    model.name );

            assertTrue(result);

            Boolean resultFalse = WaterString.containsIgnoreCaseAndDiacritic(model.name,
                    model.name + "suffix");

            assertFalse(resultFalse);
        }
    }

    public void testEnglish() {
        AppLocale.localeOverrideUsedInTests = new Locale("en");

        List<ProductModel> models = DataLoader.load(fileReader);

        assertEquals(234, models.size());

        for(ProductModel model : models) {
            Boolean result = WaterString.containsIgnoreCaseAndDiacritic(model.name + "suffix",
                    model.name );

            assertTrue(result);

            Boolean resultFalse = WaterString.containsIgnoreCaseAndDiacritic(model.name,
                    model.name + "suffix");

            assertFalse(resultFalse);
        }
    }
}
