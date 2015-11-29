package com.evgenii.waterfootprint.utils;


import android.text.TextUtils;

import java.text.Normalizer;

public class WaterString {
    public static String removeDiacritic(String text) {

        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static Boolean containsIgnoreCaseAndDiacritic(String text, String substring) {
        text = removeDiacritic(text).toLowerCase();
        substring = removeDiacritic(substring).toLowerCase();
        return text.contains(substring);
    }
}