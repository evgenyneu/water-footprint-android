package com.evgenii.waterfootprint.core;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by evgenii on 25/11/2015.
 */
public class CurrentLanguage {
    private static String defaultLanguage = "en";
    private static List<String> supportedLanguages = Arrays.asList("en", "ja", "zh", "ru");

    static String currentLanguageCode() {
        String language = AppLocale.testGetCurrentLocale().getLanguage();

        // getLanguage can return empty string when no language is set
        if (language == null || language == "") {
            return defaultLanguage;
        }

        if (!supportedLanguages.contains(language)) {
            return defaultLanguage;
        }

        return language;
    }
}
