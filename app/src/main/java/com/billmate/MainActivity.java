package com.billmate;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.*;
import android.widget.Toast;


import org.lucasr.twowayview.ItemClickSupport;
import org.lucasr.twowayview.ItemClickSupport.OnItemClickListener;
import org.lucasr.twowayview.ItemClickSupport.OnItemLongClickListener;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class MainActivity extends Activity {


    //PIE//*
    // List<PieDetailsItem> piedata=new ArrayList<PieDetailsItem>(0);
    int maxCount=0;
    int itemCount=0;
    //PIE//*
    //protected String[] employees = {"Electricity Bill", "Water Bill", "Internet Bill", "Rent", "Telephone Bill"};
    TextView bills_label,text_euro_unpaid,text_euro_paid,ElectricityAmount,ElectricityPercentage,WaterAmount,WaterPercentage,InternetAmount,InternetPercentage,OtherAmount,OtherPercentage;
    EditText billName;
    TextView paid_or_unpaid;
    CircleButton btnAddRoommate,btnAddBill;
    ImageButton image_button,imageButton_bills,imageButton_statistics,imageButton_roommates;
    FrameLayout layout_billmates,layout_bills,layout_statistics;
    DataHandler handler;
    Switch switch_bills;
    ArrayList<Integer> roommateBtnIds = new ArrayList<Integer>();
    String[] web = {
            "Internet Bill",
            "Water Bill",
            "Electricity Bill"
    } ;
    Integer[] imageId = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshRoommatesListView();
        refreshBillsListView(false);
        refreshStatisticListView();
        imageButton_bills = (ImageButton) findViewById(R.id.imageButton_bills);
        bills_label=(TextView)this.findViewById(R.id.textView4);
        imageButton_statistics = (ImageButton) findViewById(R.id.imageButton_statistics);
        imageButton_roommates = (ImageButton) findViewById(R.id.imageButton_roommates);
        btnAddRoommate = (CircleButton) findViewById(R.id.btnAddRoommate);
        btnAddBill = (CircleButton) findViewById(R.id.btnAddBill);
        layout_billmates=(FrameLayout)this.findViewById(R.id.layout_billmates);
        layout_bills=(FrameLayout)this.findViewById(R.id.layout_bills);
        layout_statistics=(FrameLayout)this.findViewById(R.id.layout_statistics);
      //  text_euro_unpaid=(TextView)this.findViewById(R.id.total_unpaid);
       // text_euro_paid=(TextView)this.findViewById(R.id.total_paid);
        bills_label.setText("Unpaid Bills");
        handler = new DataHandler(getBaseContext());
        handler.open();
        Cursor z = handler.returnBillsData();
        //text_euro_paid.setText(z);
        float paid=0;
        float unpaid=0;
        if(z.moveToFirst())
            do
            {
                if(z.getInt(4)==0) {
                    paid=paid+z.getFloat(1);}
                else if(z.getInt(4)==1){
                        unpaid=unpaid+z.getFloat(1);
                    }
            }while(z.moveToNext());
        handler.close();
        //Toast.makeText(getBaseContext(),"not"+paid , Toast.LENGTH_LONG).show();
       // text_euro_paid.setText(Float.toString(paid));
       // text_euro_unpaid.setText(Float.toString(unpaid));
        switch_bills = (Switch) findViewById(R.id.switch_bills);
        switch_bills.setTextOn("");

        switch_bills.setTextOff("");

        //set the switch to ON
        switch_bills.setChecked(false);
        //attach a listener to check for changes in state
        switch_bills.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {

                    bills_label.setText("Paid Bills");
                    refreshBillsListView(true);
                }
                else {

                    bills_label.setText("Unpaid Bills");
                    refreshBillsListView(false);
                }
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });

        imageButton_bills.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {//.compare(layout_billmates.getVisibility(),1)
                if (layout_bills.isShown()){
                    layout_bills.setVisibility(FrameLayout.GONE);

                }
                else{

                    layout_bills.setVisibility(FrameLayout.VISIBLE);
                }



            }
        });

        imageButton_roommates.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {//.compare(layout_billmates.getVisibility(),1)
                if (layout_billmates.isShown()){
                    layout_billmates.setVisibility(FrameLayout.GONE);

                }
                else{

                    layout_billmates.setVisibility(FrameLayout.VISIBLE); 
                    //   Toast.makeText(getBaseContext(),"not" , Toast.LENGTH_LONG).show();
                }



            }
        });
        imageButton_statistics.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {//.compare(layout_billmates.getVisibility(),1)
                if (layout_statistics.isShown()){
                    layout_statistics.setVisibility(FrameLayout.GONE);
                }
                else{

                    layout_statistics.setVisibility(FrameLayout.VISIBLE);
                    //   Toast.makeText(getBaseContext(),"not" , Toast.LENGTH_LONG).show();
                }



            }
        });

        btnAddRoommate.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddRoomateActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
        btnAddBill.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddBill.class);
                startActivity(i);

                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void refreshRoommatesListView(){

        RecyclerView list;

        final ArrayList<String> roommates_name = new ArrayList<String>();
        ArrayList<Integer> roommates_color = new ArrayList<Integer>();
        ArrayList<Integer> roommateBtnIds2 = new ArrayList<Integer>();

        handler = new DataHandler(getBaseContext());
        handler.open();
        Cursor c = handler.returnRoommatesData();
        if(c.moveToFirst())
            do
            {
                roommates_name.add(c.getString(0));

                roommates_color.add(c.getInt(1));

                roommateBtnIds2.add(c.getInt(2));

            }while(c.moveToNext());
        handler.close();
        roommateBtnIds=roommateBtnIds2;
        CustomRoommateList adapter = new
                CustomRoommateList(roommates_name, roommates_color);
        list=(RecyclerView)findViewById(R.id.listViewRoommates);
        list.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        list.setLayoutManager(llm);
        list.setLongClickable(true);
        list.setAdapter(adapter);
        final ItemClickSupport itemClick = ItemClickSupport.addTo(list);
        itemClick.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {

                int userid = roommateBtnIds.get(+position);
               // Toast.makeText(MainActivity.this, "You Clicked at "+userid, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (MainActivity.this,RoommateProfile.class);
                intent.putExtra("userid", userid);
                startActivity(intent);

                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        itemClick.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView recyclerView, View view, int i, long l) {

                return false;

            }
        });

/*
        final ItemClickSupport itemClick = ItemClickSupport.addTo(list);
        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {


            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at ", Toast.LENGTH_SHORT).show();
            }
        });*/
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(MainActivity.this, "You Clicked at " + roommates_name.get(+position), Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    public void refreshBillsListView(boolean checkedswitch){

        ListView list;
        ListView statisticsList; ///Delete it is just for testin
        final ArrayList<String> bills = new ArrayList<String>();
        ArrayList<Float> amount = new ArrayList<Float>();
        ArrayList<Integer> imageID = new ArrayList<Integer>();
        float paid_or_unpaid_amount=0;
        handler = new DataHandler(getBaseContext());
        handler.open();
        Cursor c = handler.returnBillsData();
        if(c.moveToFirst())
            do
            {
                if(!checkedswitch) {
                    if(c.getInt(4)==0) {
                        bills.add(c.getString(0));
                        amount.add(c.getFloat(1));
                        imageID.add(c.getInt(2));
                        paid_or_unpaid_amount=paid_or_unpaid_amount+c.getFloat(1);
                    }
                }
                else{
                    if(c.getInt(4)==1) {
                        bills.add(c.getString(0));
                        amount.add(c.getFloat(1));
                        imageID.add(c.getInt(2));
                        paid_or_unpaid_amount=paid_or_unpaid_amount+c.getFloat(1);
                    }

                }





            }while(c.moveToNext());
        handler.close();


        paid_or_unpaid=(TextView)this.findViewById(R.id.paid_or_unpaid);
        paid_or_unpaid.setText(Float.toString(paid_or_unpaid_amount));
        CustomList adapter = new
                CustomList(MainActivity.this, bills, imageID, amount);
        list=(ListView)findViewById(R.id.listViewBillsNotPaid);
        list.setAdapter(adapter);
        /*
        statisticsList=(ListView)findViewById(R.id.listViewStatistics);
        statisticsList.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " + bills.get(+position), Toast.LENGTH_SHORT).show();
            }
        });*/

    }
    public void refreshStatisticListView(){

        float paid_or_unpaid_amount=0;
        handler = new DataHandler(getBaseContext());
        handler.open();
        float total_electricity_amount=0;
        float total_water_amount=0;
        float total_internet_amount=0;
        float total_other_amount=0;
        float total_amount_of_bills=0;
        Cursor c = handler.returnBillsData();
        if(c.moveToFirst())
            do
            {
                    if(c.getInt(2)==2130837519)//Electricity type
                    {
                        total_electricity_amount=total_electricity_amount+c.getFloat(1);
                    }
                    if(c.getInt(2)==2130837518)//Water type
                    {
                        total_water_amount=total_water_amount+c.getFloat(1);
                    }
                    if(c.getInt(2)== 2130837517)//Internet type
                    {
                        total_internet_amount=total_internet_amount+c.getFloat(1);
                    }
                    if(c.getInt(2)==2130837520)//Other type
                    {
                        total_other_amount=total_other_amount+c.getFloat(1);
                    }
            }while(c.moveToNext());
        handler.close();
        total_amount_of_bills=total_electricity_amount+total_water_amount+total_internet_amount+total_other_amount;

        ElectricityAmount=(TextView)this.findViewById(R.id.ElectricityAmount);
        ElectricityPercentage=(TextView)this.findViewById(R.id.ElectricityPercentage);
        WaterAmount=(TextView)this.findViewById(R.id.WaterAmount);
        WaterPercentage=(TextView)this.findViewById(R.id.WaterPercentage);
        InternetAmount=(TextView)this.findViewById(R.id.InternetAmount);
        InternetPercentage=(TextView)this.findViewById(R.id.InternetPercentage);
        OtherAmount=(TextView)this.findViewById(R.id.OtherAmount);
        OtherPercentage=(TextView)this.findViewById(R.id.OtherPercentage);

        ElectricityAmount.setText("€"+Float.toString(total_electricity_amount));
        WaterAmount.setText("€"+Float.toString(total_water_amount));
        InternetAmount.setText("€"+Float.toString(total_internet_amount));
        OtherAmount.setText("€"+Float.toString(total_other_amount));


        ElectricityPercentage.setText(String.format("%.1f", total_electricity_amount/total_amount_of_bills*100)+"%");
        WaterPercentage.setText(String.format("%.1f", total_water_amount/total_amount_of_bills*100)+"%");
        InternetPercentage.setText(String.format("%.1f", total_internet_amount/total_amount_of_bills*100)+"%");
        OtherPercentage.setText(String.format("%.1f", total_other_amount/total_amount_of_bills*100)+"%");


    }
}
