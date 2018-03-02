package com.bcit.a00998715.mycontacts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bcit.a00998715.mycontacts.data.Contact;

/**
 * Created by edz on 2018-03-02.
 */

public class DBAdapter {
    //various constants to be used in creating and updating the database
    static final String KEY_ROWID = "_id";
    static final String KEY_FIRST_NAME = "first_name";
    static final String KEY_LAST_NAME = "last_name";
    static final String KEY_PHONE = "phone";
    static final String KEY_EMAIL = "email";
    static final String KEY_STREET_ADDRESS = "street_address";
    static final String KEY_PROVINCE = "province";
    static final String KEY_POSTAL_CODE = "postal_code";
    static final String KEY_CITY = "city";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
            "create table contacts (_id integer primary key autoincrement, "
                    + "first_name text not null," +
                    " last_name text not null," +
                    " phone text not null," +
                    " email text not null," +
                    "street_address text not null," +
                    "city," +
                    "province text not null," +
                    "postal_code text not null );";

    final Context context;


    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        public DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //onCreate implicitly used to create the database table "contacts"
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //onUpgrade called implicitly when the database "version" has changed
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    //calls SQLiteOpenHelper.getWritableDatabase() when opening the DB.
    //If the DB does not yet exist it will be created here.
    public DBAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        dbHelper.close();
    }

    //---insert a contact into the database---
    //uses ContentValues class to store key/value pairs for field names and data
    //to be inserted into the DB table by SQLiteDatabase.insert()
    public long insertContact(Contact contact)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_FIRST_NAME, contact.getFirstName());
        initialValues.put(KEY_LAST_NAME, contact.getLastName());
        initialValues.put(KEY_PHONE, contact.getPhoneNumber());
        initialValues.put(KEY_EMAIL, contact.getEmail());
        initialValues.put(KEY_STREET_ADDRESS, contact.getStreetAddress());
        initialValues.put(KEY_CITY, contact.getCity());
        initialValues.put(KEY_PROVINCE, contact.getProvince());
        initialValues.put(KEY_POSTAL_CODE, contact.getPostalCode());
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    //removes from the DB the record specified using SQLiteDatabase.delete()
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    //SQLiteDatabase.query builds a SELECT query and returns a "Cursor" over the result set
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {
                KEY_ROWID,
                KEY_FIRST_NAME,
                KEY_LAST_NAME,
                KEY_PHONE,
                KEY_EMAIL,
                KEY_STREET_ADDRESS,
                KEY_CITY,
                KEY_PROVINCE,
                KEY_POSTAL_CODE
        }, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_FIRST_NAME,
                        KEY_LAST_NAME,
                        KEY_PHONE,
                        KEY_EMAIL,
                        KEY_STREET_ADDRESS,
                        KEY_CITY,
                        KEY_PROVINCE,
                        KEY_POSTAL_CODE}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    //Uses SQLiteDatabase.update() to change existing data by key/value pairs
    public boolean updateContact(Contact contact)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_FIRST_NAME, contact.getFirstName());
        args.put(KEY_LAST_NAME, contact.getLastName());
        args.put(KEY_PHONE, contact.getPhoneNumber());
        args.put(KEY_EMAIL, contact.getEmail());
        args.put(KEY_STREET_ADDRESS, contact.getStreetAddress());
        args.put(KEY_CITY, contact.getCity());
        args.put(KEY_PROVINCE, contact.getProvince());
        args.put(KEY_POSTAL_CODE, contact.getPostalCode());
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + contact.getId(), null) > 0;
    }
}
