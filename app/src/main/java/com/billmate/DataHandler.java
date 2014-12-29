package com.billmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {
    public static final String BillID="id";
    public static final String BillName="name";
    public static final String BillAmount="amount";
    public static final String BillStatus="status";
    public static final String BillImageID="imageid";
    public static final String BillUserFKey="userfkey";
    public static final String RoommateID="id";
    public static final String RoommateName="name";
    public static final String RoommateColor="color";
    public static final String Table_bills="bills";
    public static final String Table_roommates="roomates";
    public static final String DATABASE_NAME="BillmateDB";
    public static final String BILLS_TABLE_CREATE="create table "+Table_bills+"(id integer primary key autoincrement"
            +", name text"
            +", amount float"
            +", status boolean"
            +", imageid integer"
            +", userfkey integer);";


    public static final String ROOMMATES_TABLE_CREATE="create table "+Table_roommates+"(id integer primary key autoincrement"
            +", name text"
            +", color int);";

    public static final int DATABASE_VERSION=1;

    Context ctx;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    public DataHandler(Context ctx)
    {
        this.ctx = ctx;
        dbHelper = new DatabaseHelper(ctx);

    }


    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        public DatabaseHelper(Context ctx ) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);  	// this is used to create an instance of SQLiteOpenHelper
// Y open helper?? so that v can open a database

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try{
                db.execSQL(BILLS_TABLE_CREATE);
            }catch (SQLiteException e)
            {
                e.printStackTrace();
            }

            try{
                db.execSQL(ROOMMATES_TABLE_CREATE);
            }catch (SQLiteException e)
            {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub

        }

    }


    public DataHandler open()
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public long insertBillsData(String name, Float amount, int imageid)
    {
        //float amountValue = Float.parseFloat(amount);
        ContentValues content = new ContentValues();
        content.put(BillName, name);
        content.put(BillAmount, amount);
        content.put(BillStatus, 0);
        content.put(BillImageID, imageid);
        content.put(BillUserFKey,12345);
        return db.insertOrThrow(Table_bills, null, content);
    }

    public Cursor returnBillsData()
    {
        return db.query(Table_bills, new String[] {BillName,BillAmount,BillImageID,BillID,BillStatus,BillUserFKey}, null, null, null, null, null);
    }

    public long insertRoommatesData(String name, int color)
    {
        //float amountValue = Float.parseFloat(amount);
        ContentValues content = new ContentValues();
        content.put(RoommateName, name);
        content.put(RoommateColor, color);
        return db.insertOrThrow(Table_roommates, null, content);
    }

    public Cursor returnRoommatesData()
    {
        return db.query(Table_roommates, new String[] {RoommateName,RoommateColor,RoommateID}, null, null, null, null, null);
    }
    public void updateBill(int id, boolean status, int userid){

        ContentValues cv = new ContentValues();

        cv.put(BillStatus,status);

        db.update(Table_bills,cv,"id="+id,null);

        ContentValues cv2 = new ContentValues();

        cv2.put(BillUserFKey,userid);

        db.update(Table_bills,cv2,"id="+id,null);

    }

    public Cursor findProduct() {
       // String query = "Select * FROM " + Table_bills + " WHERE " + BillStatus + " =  \"" + productname + "\"";
        String query = "Select * FROM " + Table_bills + " WHERE " + BillStatus + " = 0";
        ContentValues cv = new ContentValues();

        cv.get(BillID);
        Cursor cursor = db.rawQuery(query, null);
        return db.query(Table_bills, new String[] {BillName,BillAmount,BillImageID,BillID,BillStatus}, null, null, null, null, null);
    }
}

