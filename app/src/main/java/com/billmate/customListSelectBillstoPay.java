package com.billmate;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

public class customListSelectBillstoPay extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList<String> name;
    private final ArrayList<Float> amount;
    private final ArrayList<Integer> imageId;
    private final ArrayList<Integer> billid;
    private final int userid;

    public customListSelectBillstoPay(Activity context,
                                      ArrayList<String> name, ArrayList<Integer> imageId, ArrayList<Float> amount, ArrayList<Integer> billsID, int userID) {
        super(context, R.layout.list_item_select_bill, name);
        this.context = context;
        this.name = name;
        this.amount = amount;
        this.imageId = imageId;
        this.billid=billsID;
        userid=userID;
    }
    class CustomTagObject {
        public int id;
        public boolean status;
    }

    @Override
    public View getView(int position, final View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item_select_bill, null, true);
        Hashtable hashTableTags = new Hashtable();
        hashTableTags.put("id",billid.get(position).toString());
        hashTableTags.put("status",false);
        rowView.setTag(hashTableTags);
        final TextView billName = (TextView) rowView.findViewById(R.id.billName);
        TextView billAmount = (TextView) rowView.findViewById(R.id.amount);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        final CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.checkBox);
        checkbox.setTag(hashTableTags);
        billName.setText(name.get(position).toString());
        billAmount.setText(amount.get(position).toString());
        imageView.setImageResource(imageId.get(position));
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final boolean isChecked = checkbox.isChecked();
                if(isChecked) {
                    Hashtable hashtable = new Hashtable();
                    hashtable = (Hashtable) arg0.getTag();
                    DataHandler handler;
                    handler = new DataHandler(getContext());
                    handler.open();
                    String idstring = hashtable.get("id").toString();
                    handler.updateBill(Integer.parseInt(idstring),true,userid);


                    handler.close();
                }
                else {

                    Hashtable hashtable = new Hashtable();
                    hashtable = (Hashtable) arg0.getTag();
                    DataHandler handler;
                    handler = new DataHandler(getContext());
                    handler.open();
                    String idstring = hashtable.get("id").toString();
                    handler.updateBill(Integer.parseInt(idstring), false, userid);

                    handler.close();

                }
            }
        });
        return rowView;
    }
}