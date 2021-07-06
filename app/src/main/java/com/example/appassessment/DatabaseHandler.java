package com.example.appassessment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Content";
    private static final String TABLE_NAME = "TextContent";
    private static final String KEY_ID = "id";
    private static final String KEY_APP = "appName";
    private static final String KEY_PACKAGE_NAME = "packageName";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TIMESTAMP = "timeStamp";
    private static final String TAG = "Database Handler";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PASSWORDS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_APP + " TEXT,"
                + KEY_PACKAGE_NAME + " TEXT,"
                + KEY_TEXT + " TEXT,"
                + KEY_TIMESTAMP + " TEXT" + ")";
        db.execSQL(CREATE_PASSWORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addTextContent(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_APP, model.getAppName());
        contentValues.put(KEY_PACKAGE_NAME, model.getPackageName());
        contentValues.put(KEY_TEXT, model.getText());
        contentValues.put(KEY_TIMESTAMP, model.getTimeStamp());

        db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "Added to database");
        db.close();
    }

    public List<Model> getApplicationLabel() {
        List<Model> applicationLabel = new ArrayList<Model>();
        String select_query = "SELECT DISTINCT appName,packageName FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        if (cursor.moveToFirst()) {
            do {
                Model model = new Model();
                model.setAppName(cursor.getString(0));
                model.setPackageName(cursor.getString(1));
                applicationLabel.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return applicationLabel;
    }

    public List<Model> getTextContent(String currentAppName) {
        List<Model> textContent = new ArrayList<Model>();
        String select_query = "SELECT * FROM " + TABLE_NAME
                + " WHERE appName LIKE '%" + currentAppName + "%' ORDER BY " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        if (cursor.moveToFirst()) {
            do {
                Model model = new Model();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setAppName(cursor.getString(1));
                model.setPackageName(cursor.getString(2));
                model.setText(cursor.getString(3));
                model.setTimeStamp(cursor.getString(4));
                textContent.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return textContent;
    }

    public void clearTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
