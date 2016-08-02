package com.chalmergps.jacobth.gpsvolumecalc;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jacobth on 2016-05-02.
 */
public class CustomSaveAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemName;
    private final List<Integer> imgId;
    private final List<String> dates;
    private final Database database;
    private final List<Integer> idList;

    public CustomSaveAdapter(Activity context, List<String> itemName, List<Integer> imgId, List<String> dates, List<Integer> idList) {
        super(context, R.layout.rowlayout, itemName);

        this.context = context;
        this.itemName =itemName;
        this.imgId =imgId;
        this.dates = dates;
        this.idList = idList;
        database = new Database(context);
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.saverowlayout, null,true);

        TextView dateText = (TextView) rowView.findViewById(R.id.dateSave);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.iconSave);
        TextView nameText = (TextView) rowView.findViewById(R.id.nameSave);

        imageView.setImageResource(imgId.get(position));
        nameText.setText(itemName.get(position));
        dateText.setText(dates.get(position));

        ImageButton deleteBtn = (ImageButton)rowView.findViewById(R.id.deleteSave);
        deleteBtn.setFocusable(false);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                handleDialog(idList.get(position), position);
            }
        });

        return rowView;
    }

    private void handleDialog(final int id, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.deleteSave(id);
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                localRemove(position);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void localRemove(int position) {
        idList.remove(position);
        imgId.remove(position);
        itemName.remove(position);
        dates.remove(position);
    }
}
