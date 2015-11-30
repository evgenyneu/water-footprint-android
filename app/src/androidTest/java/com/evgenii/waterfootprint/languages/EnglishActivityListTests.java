package com.evgenii.waterfootprint.languages;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.evgenii.waterfootprint.ProductListActivity;
import com.evgenii.waterfootprint.R;
import com.evgenii.waterfootprint.core.AppLocale;
import com.evgenii.waterfootprint.core.DataLoader;
import com.evgenii.waterfootprint.search_helpers.WaterRunTime;

import java.util.Locale;


public class EnglishActivityListTests extends ActivityInstrumentationTestCase2<ProductListActivity> {
    private ProductListActivity mActivity;

    public EnglishActivityListTests() {
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

    public void testShowProducts() {
        ListView listView = (ListView) mActivity.findViewById(R.id.listview);
        ListAdapter adapter = listView.getAdapter();
        assertEquals(234, adapter.getCount());

        // Show row
        // ---------

        View view = adapter.getView(145, null, null);

        TextView nameTextView = (TextView) view
                .findViewById(R.id.product_name);

        assertEquals("Palm kernel oil", nameTextView.getText());

        TextView waterFootprintTextView = (TextView) view
                .findViewById(R.id.product_water_footprint);

        assertEquals("5,401", waterFootprintTextView.getText());
    }

    public void testSearch() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.didChangeSearch("Meat");
            }
        });

        getInstrumentation().waitForIdleSync();

        ListView listView = (ListView) mActivity.findViewById(R.id.listview);
        ListAdapter adapter = listView.getAdapter();
        assertEquals(5, adapter.getCount());

        // Show row
        // ---------

        View view = adapter.getView(0, null, null);

        TextView nameTextView = (TextView) view
                .findViewById(R.id.product_name);

        assertEquals("Beef", nameTextView.getText());
    }

    public void testSearchPerformance() {
        final WaterRunTime runTime = new WaterRunTime();

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                runTime.startTime = System.currentTimeMillis();
                mActivity.didChangeSearch("beef meat");
                runTime.stopTime = System.currentTimeMillis();
            }
        });

        getInstrumentation().waitForIdleSync();

        long timeElapsedMilliseconds = runTime.stopTime - runTime.startTime;
        assertEquals(282347, timeElapsedMilliseconds);
    }
}
