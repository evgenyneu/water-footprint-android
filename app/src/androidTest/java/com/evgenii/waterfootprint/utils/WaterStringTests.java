package com.evgenii.waterfootprint.utils;

import android.test.AndroidTestCase;
import android.text.TextUtils;

import java.util.Locale;

public class WaterStringTests extends AndroidTestCase {
    public void testRemoveDiactiric() {
        assertEquals("черныи", WaterString.removeDiacritic("чёрный"));
        assertEquals("Joao", WaterString.removeDiacritic("Joáo"));

        assertEquals("甘蓝，小圆白菜，小卷心菜，芽卷心菜，芽甘蓝",
                WaterString.removeDiacritic("甘蓝，小圆白菜，小卷心菜，芽卷心菜，芽甘蓝"));

        assertEquals("エンドウ豆（乾燥)", WaterString.removeDiacritic("エンドウ豆（乾燥)"));
    }
}