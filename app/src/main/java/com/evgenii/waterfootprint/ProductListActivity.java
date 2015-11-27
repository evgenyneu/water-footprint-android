package com.evgenii.waterfootprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.evgenii.waterfootprint.core.CurrentLanguage;
import com.evgenii.waterfootprint.core.DataLoader;
import com.evgenii.waterfootprint.core.ProductModel;
import com.evgenii.waterfootprint.list.ProductsAdapter;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReader;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private BroadcastReceiver localeChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        loadProductList();
        registerLocaleChange();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(localeChangeReceiver);
    }

    private void registerLocaleChange() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.LOCALE_CHANGED");

        localeChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                DataLoader.productsCache = null;
            }
        };

        registerReceiver(localeChangeReceiver, filter);
    }

    private void loadProductList() {
        final ListView listview = (ListView) findViewById(R.id.listview);

        AssetsFileReaderInterface assetsFileReader = new AssetsFileReader(this);
        List<ProductModel> products = DataLoader.loadCached(assetsFileReader);
        ProductsAdapter adapter =  new ProductsAdapter(this, products);
        listview.setAdapter(adapter);
    }
}

