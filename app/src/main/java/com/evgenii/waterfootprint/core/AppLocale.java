package com.evgenii.waterfootprint.core;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AppLocale {
    public static Locale localeOverrideUsedInTests;
    private static List<String> ignoreDiacriticForLanguages = Arrays.asList("ru");

    public static Locale testGetCurrentLocale() {
        if (localeOverrideUsedInTests != null) { return localeOverrideUsedInTests; }
        return Locale.getDefault();
    }

    public static Boolean ignoreDiacritic() {
        return ignoreDiacriticForLanguages.contains(testGetCurrentLocale().getLanguage());
    }
}
