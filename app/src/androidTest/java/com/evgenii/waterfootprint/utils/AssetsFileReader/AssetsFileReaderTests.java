package com.evgenii.waterfootprint.utils.AssetsFileReader;

import android.test.InstrumentationTestCase;

import java.io.IOException;

public class AssetsFileReaderTests extends InstrumentationTestCase {
    protected AssetsFileReader assetsFileReader;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        assetsFileReader = new AssetsFileReader(
                getInstrumentation().getTargetContext().getApplicationContext());
    }

    public void testReadFile() throws IOException {
        String result = assetsFileReader
                .ReadFile("data/data_en.tsv");

        assertTrue(result.startsWith("Abaca fibre"));
    }
}
