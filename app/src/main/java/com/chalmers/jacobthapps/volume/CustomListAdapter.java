package com.chalmers.jacobthapps.volume;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemName;
    private final List<Integer> imgId;

    public CustomListAdapter(Activity context, List<String> itemName, List<Integer> imgId) {
        super(context, R.layout.rowlayout, itemName);

        this.context=context;
        this.itemName =itemName;
        this.imgId =imgId;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.rowlayout, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.pos);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView title = (TextView) rowView.findViewById(R.id.titleListText);

        imageView.setImageResource(imgId.get(position));
        title.setText("Position " + (position + 1));
        txtTitle.setText(itemName.get(position));

        return rowView;
    }
}
