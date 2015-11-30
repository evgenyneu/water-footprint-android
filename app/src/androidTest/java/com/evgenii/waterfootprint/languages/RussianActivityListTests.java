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

public class RussianActivityListTests extends ActivityInstrumentationTestCase2<ProductListActivity> {
    private ProductListActivity mActivity;

    public RussianActivityListTests() {
        super(ProductListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        AppLocale.localeOverrideUsedInTests = new Locale("ru");
        mActivity = getActivity();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        AppLocale.localeOverrideUsedInTests = null;
        DataLoader.productsCache = null;
    }

    public void testShowProducts() {
        ListView listView = (ListView) mActivity.findViewById(R.id.listview);
        ListAdapter adapter = listView.getAdapter();
        assertEquals(230, adapter.getCount());

        // Show row
        // ---------

        View view = adapter.getView(145, null, null);

        TextView nameTextView = (TextView) view
                .findViewById(R.id.product_name);

        assertEquals("Пшеничный крахмал", nameTextView.getText());

        TextView waterFootprintTextView = (TextView) view
                .findViewById(R.id.product_water_footprint);

        assertEquals("1 436", waterFootprintTextView.getText());
    }

    public void testSearch() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.didChangeSearch("черныи");
            }
        });

        getInstrumentation().waitForIdleSync();

        ListView listView = (ListView) mActivity.findViewById(R.id.listview);
        ListAdapter adapter = listView.getAdapter();
        assertEquals(3, adapter.getCount());

        // Show row
        // ---------

        View view = adapter.getView(0, null, null);

        TextView nameTextView = (TextView) view
                .findViewById(R.id.product_name);

        assertEquals("Перец свежий", nameTextView.getText());
    }

    public void testSearchPerformance() {
        final WaterRunTime runTime = new WaterRunTime();

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                runTime.startTime = System.nanoTime();
                mActivity.didChangeSearch("черныи черныи");
                runTime.stopTime = System.nanoTime();;
            }
        });

        getInstrumentation().waitForIdleSync();

        double timeElapsedMilliseconds = (runTime.stopTime - runTime.startTime) / 1000_000.0;
        assertEquals(345435, timeElapsedMilliseconds);
    }
}
