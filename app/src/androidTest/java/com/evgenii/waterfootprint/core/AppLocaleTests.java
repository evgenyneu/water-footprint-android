package com.evgenii.waterfootprint.core;

import android.test.AndroidTestCase;

import java.util.Locale;

public class AppLocaleTests extends AndroidTestCase {

    protected void tearDown() throws Exception {
        super.tearDown();
        AppLocale.localeOverrideUsedInTests = null;
    }

    public void testGetCurrentLocale() {
        AppLocale.localeOverrideUsedInTests = Locale.TAIWAN;
        Locale result = AppLocale.testGetCurrentLocale();

        assertEquals(Locale.TAIWAN, result);
    }

    // Ignore diacritic
    // ----------------------

    public void testIgnoreDiacritic_russian() {
        AppLocale.localeOverrideUsedInTests = new Locale("ru");
        assertTrue(AppLocale.ignoreDiacritic());
    }

    public void testDoNotIgnoreDiacritic_english() {
        AppLocale.localeOverrideUsedInTests = Locale.ENGLISH;
        assertFalse(AppLocale.ignoreDiacritic());
    }

    public void testDoNotIgnoreDiacritic_chinese() {
        AppLocale.localeOverrideUsedInTests = Locale.SIMPLIFIED_CHINESE;
        assertFalse(AppLocale.ignoreDiacritic());
    }

    public void testDoNotIgnoreDiacritic_japanese() {
        AppLocale.localeOverrideUsedInTests = Locale.JAPAN;
        assertFalse(AppLocale.ignoreDiacritic());
    }
}
