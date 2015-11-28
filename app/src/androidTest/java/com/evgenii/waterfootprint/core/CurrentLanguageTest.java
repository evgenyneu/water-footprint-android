package com.evgenii.waterfootprint.core;
import android.test.AndroidTestCase;
import com.evgenii.waterfootprint.core.CurrentLanguage;

import java.util.Locale;

public class CurrentLanguageTest extends AndroidTestCase {

    protected void tearDown() throws Exception {
        super.tearDown();
        AppLocale.localeOverrideUsedInTests = null;
    }

    public void testGetCurrentLanguage() {
        AppLocale.localeOverrideUsedInTests = Locale.SIMPLIFIED_CHINESE;
        String result = CurrentLanguage.currentLanguageCode();
        assertEquals("zh", result);
    }

    public void testGetEnglishLanguageIfUnsupportedLanguage() {
        AppLocale.localeOverrideUsedInTests = Locale.KOREA;
        String result = CurrentLanguage.currentLanguageCode();
        assertEquals("en", result);
    }
}
