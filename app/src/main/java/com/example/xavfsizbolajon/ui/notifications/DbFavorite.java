package com.example.xavfsizbolajon.ui.notifications;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DbFavorite  extends SQLiteOpenHelper {

    private static final String DB_NAME = "DbFavorite";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "mycourses";
    private static final String ID_COL = "id";
    private static final String VIDEOID = "vieoId";
    private static final String VIDEONAME = "videoName";

    // creating a constructor for our database handler.
    public DbFavorite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VIDEOID + " TEXT,"
                + VIDEONAME + " TEXT)";
        db.execSQL(query);
    }
    public void addNewCourse(String vieoId, String videoName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(ID_COL, id);
        values.put(VIDEOID, vieoId);
        values.put(VIDEONAME, videoName);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<NotificationsViewModel> readCourses() {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<NotificationsViewModel> courseModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                int Id = Integer.parseInt(cursorCourses.getString(0));
                String vieoId = cursorCourses.getString(1);
                String videoName = cursorCourses.getString(2);
//                courseModalArrayList.add(new HistoryModel(
//                        cursorCourses.getString(1),
//                        cursorCourses.getString(2)
//                ));

                courseModalArrayList.add(new NotificationsViewModel(Id, vieoId, videoName));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return courseModalArrayList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public  void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }


    public void deleteSelect(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID_COL + " = ?", new String[]{id});
        db.close();
    }


    public ArrayList<NotificationsViewModel> searchCourses(String query) {
        ArrayList<NotificationsViewModel> searchList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Мос келадиган қидириш учун аниқ мослик
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + VIDEOID + " LIKE ?", new String[]{ query.trim() + "%"});   // query.trim() пробелни йўқ қилади. "%" ўхшашларини топади
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + WORD + " LIKE ?", new String[]{query.trim()});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int Id = cursor.getInt(cursor.getColumnIndex(ID_COL));
                @SuppressLint("Range") String word = cursor.getString(cursor.getColumnIndex(VIDEOID));
                @SuppressLint("Range") String translate = cursor.getString(cursor.getColumnIndex(VIDEONAME));

                searchList.add(new NotificationsViewModel(Id, word, translate));
            } while (cursor.moveToNext());
        }
        Log.d("demo45", "Қидирув сўрови: " + query);
        Log.d("demo45", "Натижалар сони: " + searchList.size());
        cursor.close();
        return searchList;
    }
}