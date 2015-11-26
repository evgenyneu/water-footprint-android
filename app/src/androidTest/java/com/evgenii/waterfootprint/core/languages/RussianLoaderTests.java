package com.evgenii.waterfootprint.core.languages;

import android.test.InstrumentationTestCase;

import com.evgenii.waterfootprint.core.CurrentLanguage;
import com.evgenii.waterfootprint.core.DataLoader;
import com.evgenii.waterfootprint.core.ProductModel;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReader;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import java.util.List;

public class RussianLoaderTests extends InstrumentationTestCase {

    AssetsFileReaderInterface fileReader;

    protected void setUp() {
        CurrentLanguage.languageCodeCache = "ru";

        fileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());
    }

    protected void tearDown() {
        CurrentLanguage.languageCodeCache = null;
        DataLoader.productsCache = null;
    }

    public void testLoadData() {
        List<ProductModel> result = DataLoader.loadCached(fileReader);

        assertEquals(230, result.size());

        // Product first
        // -----------

        ProductModel productFirst = result.get(0);
        assertEquals("Абака, волокно", productFirst.name);
        assertEquals("", productFirst.synonyms);
        assertEquals((int)22654, (int)productFirst.waterLitres);

        // Product middle
        // -----------

        ProductModel productMiddle = result.get(121);
        assertEquals("Нут", productMiddle.name);
        assertEquals("Турецкий орех, бараний горох, шиш", productMiddle.synonyms);
        assertEquals((int)4177, (int)productMiddle.waterLitres);

        // Product last
        // -----------

        ProductModel productLast = result.get(result.size() - 1);
        assertEquals("Ячмень", productLast.name);
        assertEquals("", productLast.synonyms);
        assertEquals((int)1423, (int)productLast.waterLitres);
    }

    public void testCheckDataTrimmed() {
        List<ProductModel> models = DataLoader.loadCached(fileReader);

        for(ProductModel model : models) {
            assertEquals(model.name, model.name.trim());
            assertEquals(model.synonyms, model.synonyms.trim());
        }
    }
}
