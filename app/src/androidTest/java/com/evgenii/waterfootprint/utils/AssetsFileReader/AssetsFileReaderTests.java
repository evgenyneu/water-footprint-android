package com.evgenii.waterfootprint.utils.AssetsFileReader;

import android.test.InstrumentationTestCase;

import java.io.IOException;

public class AssetsFileReaderTests extends InstrumentationTestCase {
    protected AssetsFileReader assetsFileReader;

    @Override
    protected void setUp() {
        assetsFileReader = new AssetsFileReader(getInstrumentation()
                .getContext());
    }

    public void testReadFile() throws IOException {
        String result = assetsFileReader
                .ReadFile("data/data_en.tsv");
        assertEquals("Hello\nWorld!\n日本語", result);
    }
}
