package com.evgenii.waterfootprint.core;

import java.util.Locale;

public class AppLocale {
    public static Locale localeOverrideUsedInTests;

    public static Locale testGetCurrentLocale() {
        if (localeOverrideUsedInTests != null) { return localeOverrideUsedInTests; }
        return Locale.getDefault();
    }
}
