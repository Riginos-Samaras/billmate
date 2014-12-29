package com.billmate;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import at.markushi.ui.CircleButton;


public class RoommateProfile extends Activity {
    DataHandler handler;
    Integer userID=13;
    float totalAmmountpaid=0;
    CircleButton btnBack;
    float totalAmmountunpaid=0;
    int total_users;
    float amountcurrentuserpaid=0;
    String user_name="nothing";
    TextView dueuntilpayoff,duetoroomates,roomates_dues,payoff_dues;
    Map<Integer,String> users = new HashMap<Integer,String>();
    ArrayList<Integer> selectedBillsID = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roommate_profile);
        Intent intent = getIntent();
        int userid = intent.getIntExtra("userid",0);
        userID=userid;
        fetchUsersInfo();
       // TextView username = (TextView)findViewById(R.id.textViewRoomateName);
       // username.setText(users.get(userID));


        btnBack= (CircleButton)findViewById(R.id.btnCancel);
        btnBack.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                goBackActivity();

            }
        });
        handler = new DataHandler(getBaseContext());
        handler.open();
        Cursor f = handler.returnRoommatesData();
        if(f.moveToFirst())
            do
            {
                if(f.getInt(2)==userID)
                    user_name=f.getString(0);

            }while(f.moveToNext());
        total_users=f.getCount();

        Toast.makeText(RoommateProfile.this, "Welcome "+user_name , Toast.LENGTH_SHORT).show();

        handler.close();


        handler = new DataHandler(getBaseContext());
        handler.open();
        Cursor c = handler.returnBillsData();
        if(c.moveToFirst())
            do
            {
                if(c.getInt(4)==1) {
                    totalAmmountpaid = totalAmmountpaid + c.getFloat(1);
                }
                else if(c.getInt(4)==0){
                    totalAmmountunpaid = totalAmmountunpaid + c.getFloat(1);
                }
            }while(c.moveToNext());
        handler.close();

        // Toast.makeText(RoommateProfile.this, "paid:" +totalAmmountpaid+" unpaid:"+totalAmmountunpaid, Toast.LENGTH_SHORT).show();
        refreshBillsListUnpaid();
        refreshBillsListPaid();


        CircleButton refresh = (CircleButton)findViewById(R.id.button_pay);
        CircleButton refresh_unpay = (CircleButton)findViewById(R.id.button_unpay);
        refresh.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                refreshBillsListUnpaid();
                refreshBillsListPaid();

            }
        });
        refresh_unpay.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                refreshBillsListUnpaid();
                refreshBillsListPaid();

            }
        });
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_roommate_profile, menu);
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
    public void refreshBillsListUnpaid(){

        ListView list;

        final ArrayList<String> bills = new ArrayList<String>();
        ArrayList<Float> amount = new ArrayList<Float>();
        ArrayList<Integer> imageID = new ArrayList<Integer>();
        ArrayList<Integer> BillsID = new ArrayList<Integer>();

        handler = new DataHandler(getBaseContext());
        handler.open();
        Cursor c = handler.returnBillsData();
        if(c.moveToFirst())
            do
            {
                if(c.getString(4).equals("0")) {
                    bills.add(c.getString(0));

                    amount.add(c.getFloat(1));

                    imageID.add(c.getInt(2));

                    BillsID.add(c.getInt(3));
                }

            }while(c.moveToNext());
        handler.close();

        customListSelectBillstoPay adapter = new
                customListSelectBillstoPay(RoommateProfile.this, bills, imageID, amount,BillsID,userID);
        list=(ListView)findViewById(R.id.listViewSelectBills);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Hashtable tags;
                tags =(Hashtable) view.getTag();


               // Toast.makeText(RoommateProfile.this, "You Clicked at " + tags.get("id"), Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void goBackActivity(){

        Intent intent = new Intent (RoommateProfile.this,MainActivity.class);

        startActivity(intent);

        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void refreshBillsListPaid(){

        ListView list;

        final ArrayList<String> bills = new ArrayList<String>();
        ArrayList<Float> amount = new ArrayList<Float>();
        ArrayList<Integer> imageID = new ArrayList<Integer>();
        ArrayList<Integer> BillsID = new ArrayList<Integer>();
        amountcurrentuserpaid=0;
        handler = new DataHandler(getBaseContext());
        handler.open();
        Cursor c = handler.returnBillsData();
        if(c.moveToFirst())
            do
            {
                if(c.getInt(5)==userID) {
                    if (c.getString(4).equals("1")) {
                        bills.add(c.getString(0));

                        amount.add(c.getFloat(1));

                        amountcurrentuserpaid = amountcurrentuserpaid + c.getFloat(1);
                        imageID.add(c.getInt(2));

                        BillsID.add(c.getInt(3));
                    }
                }
            }while(c.moveToNext());
        handler.close();
        //Toast.makeText(RoommateProfile.this, "Paid "+amountcurrentuserpaid+" paid:"+totalAmmountpaid+" unpaid"+totalAmmountunpaid, Toast.LENGTH_SHORT).show();

        dueuntilpayoff=(TextView)this.findViewById(R.id.dueuntilpayoff);
        duetoroomates=(TextView)this.findViewById(R.id.duetoroomates);
        roomates_dues=(TextView)this.findViewById(R.id.roomates_dues);
        payoff_dues=(TextView)this.findViewById(R.id.payoff_dues);

        //dueuntilpayoff.setText(String.format("%.1f",totalAmmountunpaid/total_users-amountcurrentuserpaid));

       if( totalAmmountpaid/total_users-amountcurrentuserpaid>=0){

           duetoroomates.setText(String.format("%.1f",totalAmmountpaid/total_users-amountcurrentuserpaid));
           roomates_dues.setText("Due to roomates:");
       }
        else {
           duetoroomates.setText(String.format("%.1f",-(totalAmmountpaid/total_users-amountcurrentuserpaid)));
            roomates_dues.setText("Roomates owes you:");

       }
        customListSelectBillstoPayUndo adapter = new
                customListSelectBillstoPayUndo(RoommateProfile.this, bills, imageID, amount,BillsID, userID);
        list=(ListView)findViewById(R.id.listViewPaidBills);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Hashtable tags;
                tags =(Hashtable) view.getTag();


                //Toast.makeText(RoommateProfile.this, "You Clicked at " + tags.get("id"), Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void fetchUsersInfo(){

        handler = new DataHandler(getBaseContext());
        handler.open();
        Cursor c = handler.returnRoommatesData();
        if(c.moveToFirst())
            do
            {
                users.put(c.getInt(0), c.getString(2));


            }while(c.moveToNext());
        handler.close();

    }

}
