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
    static String languageCodeCache = null;

    static String currentLanguageCode() {
        if (languageCodeCache == null) {
            languageCodeCache = Locale.getDefault().getLanguage();

            // getLanguage can return empty string when no language is set
            if (languageCodeCache == null || languageCodeCache == "") {
                languageCodeCache = defaultLanguage;
            }
        }

        if (!supportedLanguages.contains(languageCodeCache)) {
            languageCodeCache = defaultLanguage;
        }

        return languageCodeCache;
    }
}
