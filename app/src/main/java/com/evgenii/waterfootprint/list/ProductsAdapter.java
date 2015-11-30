package com.evgenii.waterfootprint.list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.evgenii.waterfootprint.R;
import com.evgenii.waterfootprint.core.AppLocale;
import com.evgenii.waterfootprint.core.ProductModel;

import java.text.NumberFormat;
import java.util.List;

public class ProductsAdapter extends ArrayAdapter<ProductModel> {
    private final Activity context;
    public List<ProductModel> products;

    public ProductsAdapter(Activity context, List<ProductModel> products) {
        super(context, R.layout.product_row, products);
        this.context = context;
        this.products = products;
    }

    static class ProductViewHolder {
        public TextView product_name;
        public TextView product_water_footprint;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.product_row, null);

            ProductViewHolder viewHolder = new ProductViewHolder();
            viewHolder.product_name = (TextView) rowView.findViewById(R.id.product_name);

            viewHolder.product_water_footprint = (TextView) rowView.findViewById(
                    R.id.product_water_footprint);

            rowView.setTag(viewHolder);
        }

        // Fill data
        ProductViewHolder holder = (ProductViewHolder) rowView.getTag();
        ProductModel product = products.get(position);
        holder.product_name.setText(product.name);
        String waterLitresFormatted = ProductsAdapter.formatWaterFootprint(product.waterLitres);
        holder.product_water_footprint.setText(waterLitresFormatted);

        return rowView;
    }

    public static String formatWaterFootprint(Integer value) {
        NumberFormat numberFormat = NumberFormat.getIntegerInstance(AppLocale.testGetCurrentLocale());
        return numberFormat.format(value);
    }
}
