package com.evgenii.waterfootprint;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.evgenii.waterfootprint.core.AppLocale;
import com.evgenii.waterfootprint.core.DataLoader;

import java.util.Locale;


public class SearchPerformanceTests extends ActivityInstrumentationTestCase2<ProductListActivity> {
    private ProductListActivity mActivity;

    public SearchPerformanceTests() {
        super(ProductListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        AppLocale.localeOverrideUsedInTests = Locale.ENGLISH;
        mActivity = getActivity();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        AppLocale.localeOverrideUsedInTests = null;
        DataLoader.productsCache = null;
    }

    class SearchTime {
        long startTime;
        long stopTime;
    }

    public void testSearchPerformance() {
        final SearchTime searchTime = new SearchTime();

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchTime.startTime = System.currentTimeMillis();
                mActivity.didChangeSearch("beef meat");
                searchTime.stopTime = System.currentTimeMillis();
            }
        });

        getInstrumentation().waitForIdleSync();

        long timeElapsedMilliseconds = searchTime.stopTime - searchTime.startTime;
        assertEquals(2, timeElapsedMilliseconds);
    }
}
