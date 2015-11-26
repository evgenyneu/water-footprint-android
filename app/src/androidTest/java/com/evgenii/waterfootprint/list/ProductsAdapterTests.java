package com.evgenii.waterfootprint.list;

import android.test.AndroidTestCase;

public class ProductsAdapterTests extends AndroidTestCase {
    public void testFormatWaterFootprint() {
        String result = ProductsAdapter.formatWaterFootprint(12_543);
        assertEquals("12,543", result);
    }
}
