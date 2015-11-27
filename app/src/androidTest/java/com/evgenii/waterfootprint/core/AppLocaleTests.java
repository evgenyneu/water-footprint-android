package com.evgenii.waterfootprint.core;

import android.test.AndroidTestCase;

import java.util.Locale;

public class AppLocaleTests extends AndroidTestCase {
    public void testGetCurrentLocale() {
        AppLocale.localeOverrideUsedInTests = Locale.TAIWAN;
        Locale result = AppLocale.testGetCurrentLocale();

        assertEquals(Locale.TAIWAN, result);
    }
}
