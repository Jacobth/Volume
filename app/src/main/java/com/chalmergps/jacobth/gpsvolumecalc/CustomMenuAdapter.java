package com.chalmergps.jacobth.gpsvolumecalc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomMenuAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemName;
    private final List<Integer> imgId;

    public CustomMenuAdapter(Activity context, List<String> itemName, List<Integer> imgId) {
        super(context, R.layout.menurowlayout, itemName);

        this.context=context;
        this.itemName =itemName;
        this.imgId =imgId;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.menurowlayout, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        imageView.setImageResource(imgId.get(position));
        txtTitle.setText(itemName.get(position));

        return rowView;
    }
}
