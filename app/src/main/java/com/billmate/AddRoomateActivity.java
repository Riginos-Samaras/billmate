package com.billmate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.*;

import at.markushi.ui.CircleButton;

import android.view.*;

public class AddRoomateActivity extends Activity {

    CircleButton blue_button,pink_button,purple_button,green_button,cyan_button,orange_button,save_button,cancel_button;
    Button general_color;
    EditText roommate_name;
    boolean colorchosen=false;
    int selectedColor;
    DataHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roomate);
        blue_button=(CircleButton)findViewById(R.id.blue_button);
        pink_button=(CircleButton)findViewById(R.id.pink_button);
        purple_button=(CircleButton)findViewById(R.id.purple_button);
        green_button=(CircleButton)findViewById(R.id.green_button);
        cyan_button=(CircleButton)findViewById(R.id.cyan_button);
        orange_button=(CircleButton)findViewById(R.id.orange_button);
        save_button=(CircleButton)findViewById(R.id.save_button);
        cancel_button=(CircleButton)findViewById(R.id.cancel_button);
        roommate_name=(EditText)findViewById(R.id.name_editText);


        general_color=(Button)findViewById(R.id.general_color_button);

        cyan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(AddRoomateActivity.this, Integer.toString(R.color.blue800), Toast.LENGTH_SHORT).show();
                selectedColor=getResources().getColor(R.color.cyan800);
                general_color.setBackgroundColor(selectedColor);
                colorchosen=true;
                general_color.setVisibility(View.VISIBLE);
            }

        });
        blue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor=getResources().getColor(R.color.blue800);
                general_color.setBackgroundColor(selectedColor);
                colorchosen=true;
                general_color.setVisibility(View.VISIBLE);
            }

        });
        pink_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor=getResources().getColor(R.color.pink800);
                general_color.setBackgroundColor(selectedColor);
                colorchosen=true;
                general_color.setVisibility(View.VISIBLE);
                //  Toast.makeText(AddRoomateActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
            }

        });
        green_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor=getResources().getColor(R.color.green);
                general_color.setBackgroundColor(selectedColor);
                colorchosen=true;
                general_color.setVisibility(View.VISIBLE);
            }

        });
        purple_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor=getResources().getColor(R.color.purple800);
                general_color.setBackgroundColor(selectedColor);
                colorchosen=true;
                general_color.setVisibility(View.VISIBLE);
            }

        });
        orange_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor=getResources().getColor(R.color.orange);
                general_color.setBackgroundColor(selectedColor);
                colorchosen=true;
                general_color.setVisibility(View.VISIBLE);
            }

        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roommate_name.getText().toString().equals("")){
                    Toast.makeText(AddRoomateActivity.this, "Choose Name", Toast.LENGTH_SHORT).show();
                }
                else if(colorchosen==false){
                    Toast.makeText(AddRoomateActivity.this, "Choose color", Toast.LENGTH_SHORT).show();
                }
                else {

                    String RoommateName=roommate_name.getText().toString();

                    handler = new DataHandler(getBaseContext());
                    handler.open();
                    long id = handler.insertRoommatesData(RoommateName,selectedColor);
                    handler.close();
                    Intent i = new Intent(AddRoomateActivity.this, MainActivity.class);
                    startActivity(i);

                }
            }

        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddRoomateActivity.this, MainActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }

        });
        /*
        pink_button=(Button)findViewById(R.id.pink_button);
        green_button=(Button)findViewById(R.id.green_button);
        purple_button=(Button)findViewById(R.id.purple_button);*/
        //purple_button.setBackground();
        blue_button.getBackground();

        //Toast.makeText(getApplicationContext(), Integer.toString(10), Toast.LENGTH_SHORT).show();

//        Button clickButton = (Button) findViewById(R.id.clickButton);
//        clickButton.setOnClickListener( new OnClickListener() {
//
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_roomate, menu);
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
}
