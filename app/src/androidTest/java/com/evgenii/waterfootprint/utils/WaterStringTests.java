package com.evgenii.waterfootprint.utils;

import android.test.AndroidTestCase;
import android.text.TextUtils;

import java.util.Locale;

public class WaterStringTests extends AndroidTestCase {
    public void testRemoveDiactiric() {
        assertEquals("черныи", WaterString.removeDiactic("чёрный"));
        assertEquals("Joao", WaterString.removeDiactic("Joáo"));
        assertEquals("エンドウ豆（乾燥)", WaterString.removeDiactic("エンドウ豆（乾燥)"));
    }
}