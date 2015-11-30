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

    // Contains ignore case
    // ------------

    public void testContainsIgnoreCase() {
        assertTrue(WaterString.containsIgnoreCase("Some long text", "long"));
        assertTrue(WaterString.containsIgnoreCase("Some long text", "Long"));
        assertTrue(WaterString.containsIgnoreCase("text", ""));
        assertFalse(WaterString.containsIgnoreCase("Some long text", "No match"));
        assertFalse(WaterString.containsIgnoreCase("", "No match"));
    }

    // Contains ignore case and diacritic
    // ------------

    public void testContainsIgnoreCaseAndDiacritic() {
        assertTrue(WaterString.containsIgnoreCaseAndDiacritic("Some long text", "long"));
        assertTrue(WaterString.containsIgnoreCaseAndDiacritic("чёрный", "черныи"));
        assertTrue(WaterString.containsIgnoreCaseAndDiacritic("чёрный", "Черныи"));
        assertTrue(WaterString.containsIgnoreCaseAndDiacritic("черныи", "чёрный"));
        assertTrue(WaterString.containsIgnoreCaseAndDiacritic("Черныи", "чёрный"));
        assertTrue(WaterString.containsIgnoreCaseAndDiacritic("text", ""));
    }

    public void testContainsIgnoreCaseAndDiacritic_doesNotContain() {
        assertFalse(WaterString.containsIgnoreCaseAndDiacritic("Some long text", "does not contain"));
        assertFalse(WaterString.containsIgnoreCaseAndDiacritic("", "text"));
    }
}