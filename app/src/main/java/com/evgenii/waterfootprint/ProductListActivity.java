package com.evgenii.waterfootprint;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.SearchView;

import com.evgenii.waterfootprint.core.DataLoader;
import com.evgenii.waterfootprint.core.ProductModel;
import com.evgenii.waterfootprint.list.ProductsAdapter;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReader;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

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
                // Clear the product cache after user changed the language.
                // The list of product will be regenerated using the new language settings.
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.my_search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


        return true;
    }
}

