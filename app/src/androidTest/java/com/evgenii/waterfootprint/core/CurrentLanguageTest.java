package com.evgenii.waterfootprint.core;
import android.test.AndroidTestCase;
import com.evgenii.waterfootprint.core.CurrentLanguage;

public class CurrentLanguageTest extends AndroidTestCase {

    protected void tearDown() {
        CurrentLanguage.languageCodeCache = null;
    }

    public void testGetCurrentLanguage() {
        CurrentLanguage.languageCodeCache = "zh";
        String result = CurrentLanguage.currentLanguageCode();
        assertEquals("zh", result);
    }

    public void testGetEnglishLanguageIfUnsupportedLanguage() {
        CurrentLanguage.languageCodeCache = "zz";
        String result = CurrentLanguage.currentLanguageCode();
        assertEquals("en", result);
    }
}
