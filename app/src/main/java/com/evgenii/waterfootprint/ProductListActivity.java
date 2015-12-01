package com.evgenii.waterfootprint;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.evgenii.waterfootprint.core.AppLocale;
import com.evgenii.waterfootprint.core.DataLoader;
import com.evgenii.waterfootprint.core.DataSearch;
import com.evgenii.waterfootprint.core.ProductModel;
import com.evgenii.waterfootprint.core.SearchCache;
import com.evgenii.waterfootprint.list.ProductsAdapter;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReader;
import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private BroadcastReceiver localeChangeReceiver;
    public SearchCache searchCache = new SearchCache();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        loadProductList();
        registerLocaleChange();
        registerKeyboardHide();
        setupInputChange();
        updateClearButton();
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
                searchCache.clear();
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

    private void registerKeyboardHide() {
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.search_edit_text).clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        };

        findViewById(R.id.units_text_view).setOnTouchListener(listener);
        findViewById(R.id.listview).setOnTouchListener(listener);
    }

    private void setupInputChange() {
        final EditText searchEditText = (EditText) findViewById(R.id.search_edit_text);

        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                didChangeSearch(searchEditText.getText().toString());
                updateClearButton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    public void didChangeSearch(String searchText) {
        final ListView listview = (ListView) findViewById(R.id.listview);
        AssetsFileReaderInterface assetsFileReader = new AssetsFileReader(this);
        List<ProductModel> products = DataLoader.loadCached(assetsFileReader);

        products = DataSearch.dataMatchingSearchText(products, searchText,
                AppLocale.ignoreDiacritic(), searchCache);

        ProductsAdapter adapter =  new ProductsAdapter(this, products);
        listview.setAdapter(adapter);
    }

    public void didTapClearSearchButton(View view) {
        final EditText searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchEditText.setText("");
    }

    public void updateClearButton() {
        final EditText searchEditText = (EditText) findViewById(R.id.search_edit_text);
        Button clearSearchButton = (Button) findViewById(R.id.clear_search_button);

        if (searchEditText.getText().toString().isEmpty()) {
            clearSearchButton.setVisibility(View.INVISIBLE);
        } else {
            clearSearchButton.setVisibility(View.VISIBLE);
        }
    }
}

