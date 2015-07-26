package com.wisdomlanna.sqlliterecycleview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by suraphol on 7/26/15 AD.
 */
public class SqlLiteFavoritePlaces extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "favorite_places";

    public SqlLiteFavoritePlaces(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE favorite ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId TEXT, " +
                "name TEXT, " +
                "address TEXT, " +
                "lat TEXT, " +
                "long TEXT )";
        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS favorite");
        // create fresh books table
        this.onCreate(db);
    }

    // Books table name
    private static final String TABLE_FAVORITE = "favorite";
    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LONG = "long";
    private static final String[] COLUMNS = {KEY_ID, KEY_USER_ID, KEY_NAME
            , KEY_ADDRESS, KEY_LAT, KEY_LONG};

    public void addFavoritePlaces(FavoritePlacesModel favoritePlacesModel) {
        Log.i("addFavoritePlaces", favoritePlacesModel.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, favoritePlacesModel.getUserId());
        values.put(KEY_NAME, favoritePlacesModel.getName());
        values.put(KEY_ADDRESS, favoritePlacesModel.getAddress());
        values.put(KEY_LAT, favoritePlacesModel.getLat());
        values.put(KEY_LONG, favoritePlacesModel.getLng());
        // 3. insert
        db.insert(TABLE_FAVORITE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        // 4. close
        db.close();
    }

    // Get All Books
    public List<FavoritePlacesModel> getAllBooks(String user_id) {
        List<FavoritePlacesModel> list = new LinkedList<FavoritePlacesModel>();
        // 1. build the query
        /*String query = "SELECT * FROM " + TABLE_FAVORITE + " WHERE "
                + KEY_USER_ID + " = " + "'001'";*/
        String query = "SELECT * FROM " + TABLE_FAVORITE + " WHERE "
                + KEY_USER_ID + " = " + "'" + user_id + "'";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build book and add it to list
        FavoritePlacesModel favoritePlacesModel = null;
        if (cursor.moveToLast()) {
            do {
                favoritePlacesModel = new FavoritePlacesModel();
                favoritePlacesModel.setId(Integer.parseInt(cursor.getString(0)));
                favoritePlacesModel.setUserId(cursor.getString(1));
                favoritePlacesModel.setName(cursor.getString(2));
                favoritePlacesModel.setAddress(cursor.getString(3));
                favoritePlacesModel.setLat(cursor.getString(4));
                favoritePlacesModel.setLng(cursor.getString(5));
                // Add book to books
                list.add(favoritePlacesModel);
            } while (cursor.moveToPrevious());
        }
        Log.i("getAllFavoritePlaces()", list.toString());
        return list;
    }

    /*// Deleting single book
    public void deleteBook(FavoritePlacesModel book) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete
        db.delete(TABLE_FAVORITE,
                KEY_ID + " = ?",
                new String[]{String.valueOf(book.getId())});
        // 3. close
        db.close();
        Log.i("deleteBook", book.toString());
    }*/
}