package com.billmate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class CustomRoommateList extends RecyclerView.Adapter<CustomRoommateList.ViewHolder> {

    private final ArrayList<String> name;
    private final ArrayList<Integer> color;

    public CustomRoommateList(ArrayList<String> roommate_name, ArrayList<Integer> roommate_color) {
        this.name = roommate_name;
        this.color = roommate_color;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomRoommateList.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_single_roomate, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.roommateName.setText(name.get(position));
        viewHolder.circleButton.setColor(color.get(position));


    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView roommateName;
        public CircleButton circleButton;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);


            roommateName = (TextView) itemLayoutView.findViewById(R.id.ButtonText);
            circleButton = (CircleButton) itemLayoutView.findViewById(R.id.btnUnknown);
        }
    }



    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return name.size();
    }
}