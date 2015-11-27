package com.evgenii.waterfootprint.list;

import android.test.AndroidTestCase;

import com.evgenii.waterfootprint.core.AppLocale;

import java.util.Locale;

public class ProductsAdapterTests extends AndroidTestCase {
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        AppLocale.localeOverrideUsedInTests = null;
    }

    public void testFormatWaterFootprint_english() {
        AppLocale.localeOverrideUsedInTests = Locale.ENGLISH;
        String result = ProductsAdapter.formatWaterFootprint(12_543);
        assertEquals("12,543", result);
    }

    public void testFormatWaterFootprint_russian() {
        AppLocale.localeOverrideUsedInTests = new Locale("ru");
        String result = ProductsAdapter.formatWaterFootprint(12_543);
        assertEquals("12 543", result);
    }
}
