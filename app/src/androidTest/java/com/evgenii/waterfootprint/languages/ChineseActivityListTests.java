package com.evgenii.waterfootprint.languages;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.evgenii.waterfootprint.ProductListActivity;
import com.evgenii.waterfootprint.R;
import com.evgenii.waterfootprint.core.AppLocale;
import com.evgenii.waterfootprint.core.DataLoader;
import com.evgenii.waterfootprint.search_helpers.WaterRunTime;

import java.util.Locale;

public class ChineseActivityListTests extends ActivityInstrumentationTestCase2<ProductListActivity> {
    private ProductListActivity mActivity;

    public ChineseActivityListTests() {
        super(ProductListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        AppLocale.localeOverrideUsedInTests = Locale.SIMPLIFIED_CHINESE;
        mActivity = getActivity();
        mActivity.searchCache.clear();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        AppLocale.localeOverrideUsedInTests = null;
        DataLoader.productsCache = null;
    }

    public void testShowProducts() {
        ListView listView = (ListView) mActivity.findViewById(R.id.listview);
        ListAdapter adapter = listView.getAdapter();
        assertEquals(233, adapter.getCount());

        // Show row
        // ---------

        View view = adapter.getView(145, null, null);

        TextView nameTextView = (TextView) view
                .findViewById(R.id.product_name);

        assertEquals("巧克力", nameTextView.getText());

        TextView waterFootprintTextView = (TextView) view
                .findViewById(R.id.product_water_footprint);

        assertEquals("17,196", waterFootprintTextView.getText());
    }

    public void testSearch() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.didChangeSearch("甘");
            }
        });

        getInstrumentation().waitForIdleSync();

        ListView listView = (ListView) mActivity.findViewById(R.id.listview);
        ListAdapter adapter = listView.getAdapter();
        assertEquals(4, adapter.getCount());

        // Show row
        // ---------

        View view = adapter.getView(0, null, null);

        TextView nameTextView = (TextView) view
                .findViewById(R.id.product_name);

        assertEquals("甘蓝", nameTextView.getText());
    }

    public void testSearchPerformance() {
        final WaterRunTime runTime = new WaterRunTime();

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                runTime.startTime = System.nanoTime();
                mActivity.didChangeSearch("甘 甘");
                runTime.stopTime = System.nanoTime();;
            }
        });

        getInstrumentation().waitForIdleSync();

        double timeElapsedMilliseconds = (runTime.stopTime - runTime.startTime) / 1000_000.0;
        assert(timeElapsedMilliseconds < 10);
    }
}