package com.evgenii.waterfootprint.core;

import android.test.AndroidTestCase;

import com.evgenii.waterfootprint.mocks.AssetsFileReaderMock;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import java.util.List;

public class DataLoaderTests extends AndroidTestCase {

    protected void tearDown() {
        CurrentLanguage.languageCodeCache = null;
    }

    public void testLoadDataForLanguage() {
        AssetsFileReaderInterface fileReaderMock = new AssetsFileReaderMock();

        List<ProductModel> result = DataLoader.loadForLanguage("en", fileReaderMock);

        assertEquals(412, result.size());
    }
}