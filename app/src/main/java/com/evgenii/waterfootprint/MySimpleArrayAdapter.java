package com.evgenii.waterfootprint;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by evgenii on 21/11/2015.
 */
public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] values;

    public MySimpleArrayAdapter(Activity context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowlayout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.icon);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String text = values[position];
        holder.text.setText(text);
        // change the icon for Windows and iPhone
        if (text.startsWith("iPhone")) {
            holder.image.setImageResource(R.drawable.myimage);
        } else {
            holder.image.setImageResource(R.drawable.another_image);
        }

        return rowView;
    }
}
