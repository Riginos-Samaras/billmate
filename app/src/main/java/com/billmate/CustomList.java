package com.billmate;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList<String> name;
    private final ArrayList<Float> amount;
    private final ArrayList<Integer> imageId;
    public CustomList(Activity context,
                      ArrayList<String> web, ArrayList<Integer> imageId, ArrayList<Float> amount) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.name = web;
        this.amount = amount;
        this.imageId = imageId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView billName = (TextView) rowView.findViewById(R.id.billName);
        TextView billAmount = (TextView) rowView.findViewById(R.id.amount);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        billName.setText(name.get(position));
        billAmount.setText(amount.get(position).toString());
        imageView.setImageResource(imageId.get(position));
        return rowView;
    }
}