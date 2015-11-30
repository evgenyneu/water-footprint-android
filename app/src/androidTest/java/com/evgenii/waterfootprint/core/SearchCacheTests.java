package com.evgenii.waterfootprint.core;

import android.test.AndroidTestCase;

public class SearchCacheTests  extends AndroidTestCase {

    SearchCache obj;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        obj = new SearchCache();
    }

    public void testGet() {
        assertEquals("hello", obj.get("Hello", false));
    }

    public void testGetIgnoreDiacritic() {
        assertEquals("черныи", obj.get("Чёрный", true));
    }

    public void testGetKeepDiacritic() {
        assertEquals("чёрный", obj.get("Чёрный", false));
    }

    public void testGetKeepInCache() {
        assertEquals("чёрный", obj.get("Чёрный", false));

        // Return text with diacritic from cache
        assertEquals("чёрный", obj.get("Чёрный", true));
    }

    public void testGetClearCache() {
        assertEquals("чёрный", obj.get("Чёрный", false));

        obj.clear();
        assertEquals("черныи", obj.get("Чёрный", true));
    }
}
