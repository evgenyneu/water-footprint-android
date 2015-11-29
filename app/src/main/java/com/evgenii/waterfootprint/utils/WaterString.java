package com.evgenii.waterfootprint.utils;


import android.text.TextUtils;

import java.text.Normalizer;

public class WaterString {
    public static String removeDiactic(String text) {

        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").replaceAll("\\s+","");
    }
}