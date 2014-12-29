package com.billmate;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import at.markushi.ui.CircleButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.content.*;


public class AddBill extends Activity {

    CircleButton btnInternet;
    CircleButton btnElectricity;
    CircleButton btnWater;
    CircleButton btnUnknown;
    CircleButton btnAdd;
    CircleButton btnBack;
    CircleButton buttonConfirm;
    EditText editTextBillName;
    EditText editTextAmount;
    TextView TextViewEuro;
    ListView list;
    DataHandler handler;
    Integer selectedType;
    ImageView img;

    final ArrayList<String> billsList = new ArrayList<String>();
    ArrayList<Float> amountList = new ArrayList<Float>();
    ArrayList<Integer> imageIDList = new ArrayList<Integer>();

    Integer[] imageId = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        btnInternet = (CircleButton)findViewById(R.id.btnInternet);
        btnElectricity = (CircleButton)findViewById(R.id.btnElectricity);
        btnWater = (CircleButton)findViewById(R.id.btnWater);
        btnUnknown = (CircleButton)findViewById(R.id.btnUnknown);
        editTextBillName = (EditText)findViewById(R.id.editTextBillType);
        editTextAmount= (EditText)findViewById(R.id.editTextAmount);
        TextViewEuro = (TextView)findViewById(R.id.TextViewEuro);
        buttonConfirm= (CircleButton)findViewById(R.id.buttonConfirm);
        img= (ImageView) findViewById(R.id.imageView);
        btnAdd= (CircleButton)findViewById(R.id.btnAccept);





        editTextBillName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    editTextAmount.requestFocus();
                }
                return false;
            }
        });



        buttonConfirm.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {


                if(billsList.size()!=0) {
                    insertBillsToDB();
                    goBackActivity();
                }
                else {

                    Toast.makeText(AddBill.this,"You haven't added anything!", Toast.LENGTH_SHORT).show();


                }

            }
        });

        editTextAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    insertEntryInListView();
                }
                return false;
            }
        });

        btnAdd.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                insertEntryInListView();

            }
        });

        btnBack= (CircleButton)findViewById(R.id.btnCancel);
        btnBack.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {


                goBackActivity();

            }
        });



        btnInternet.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                img.setImageResource(R.drawable.internet);
                editTextBillName.setVisibility(View.VISIBLE);
                editTextBillName.setText("Internet Bill");
                editTextAmount.setVisibility(View.VISIBLE);
                TextViewEuro.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                selectedType=imageId[0];
                img.setVisibility(View.VISIBLE);

            }
        });
        btnWater.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                img.setImageResource(R.drawable.image2);
                editTextBillName.setVisibility(View.VISIBLE);
                editTextBillName.setText("Water Bill");
                editTextAmount.setVisibility(View.VISIBLE);
                TextViewEuro.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                selectedType=imageId[1];
                img.setVisibility(View.VISIBLE);

            }
        });
        btnElectricity.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                img.setImageResource(R.drawable.image3);
                editTextBillName.setVisibility(View.VISIBLE);
                editTextBillName.setText("Electricity Bill");
                editTextAmount.setVisibility(View.VISIBLE);
                TextViewEuro.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                selectedType=imageId[2];
                img.setVisibility(View.VISIBLE);

            }
        });
        btnUnknown.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                img.setImageResource(R.drawable.image4);
                editTextBillName.setVisibility(View.VISIBLE);
                editTextBillName.setText("");
                editTextBillName.setHint("Bill Name");
                editTextAmount.setVisibility(View.VISIBLE);
                TextViewEuro.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                selectedType=imageId[3];
                img.setVisibility(View.VISIBLE);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_bill, menu);
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
    public void refreshBillsListView(){

        final CustomList adapter = new
                CustomList(AddBill.this, billsList, imageIDList, amountList);
        list=(ListView)findViewById(R.id.listViewBills);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(AddBill.this, "You Clicked at " + billsList.get(+position), Toast.LENGTH_SHORT).show();
            }
        });


        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        list,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }
                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {

                                    billsList.remove(position);
                                    imageIDList.remove(position);
                                    amountList.remove(position);
                                    list.setAdapter(adapter);

                                }

                            }
                        });


        list.setOnTouchListener(touchListener);

        list.setOnScrollListener(touchListener.makeScrollListener());

    }

    public void insertBillsToDB(){

        String name;
        String amount;
        int image;

        handler = new DataHandler(getBaseContext());
        handler.open();
        for (int i = 0; i < billsList.size(); i++){

            long id = handler.insertBillsData(billsList.get(i),amountList.get(i),imageIDList.get(i));  //must change
        }


        handler.close();
    }

    public void insertEntryInListView(){

        if (editTextBillName.getText().toString().equals("")) {

            Toast.makeText(AddBill.this,"Add a name to the bill first!", Toast.LENGTH_SHORT).show();

        }
        else if(editTextAmount.getText().toString().equals("")){

            Toast.makeText(AddBill.this,"You haven't set an amount for this bill", Toast.LENGTH_SHORT).show();

        }
        else {

            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);


            String billNameString = editTextBillName.getText().toString();
            String amountString = editTextAmount.getText().toString();
            float amount = Float.parseFloat(amountString);

            billsList.add(billNameString);
            amountList.add(amount);
            imageIDList.add(selectedType);
            editTextBillName.setText("");
            editTextBillName.setVisibility(View.INVISIBLE);
            TextViewEuro.setVisibility(View.INVISIBLE);
            editTextAmount.setVisibility(View.INVISIBLE);
            btnAdd.setVisibility(View.INVISIBLE);
            img.setVisibility(View.INVISIBLE);
            editTextAmount.setText("");
            refreshBillsListView();

        }

    }
    public void goBackActivity(){

        Intent intent = new Intent (AddBill.this,MainActivity.class);

        startActivity(intent);

        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

}
