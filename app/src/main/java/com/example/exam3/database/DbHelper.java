package com.example.exam3.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.exam3.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ProductDatabase";
    // Country table name
    private static final String TABLE_PRODUCT = "Products";
    // Country Table Columns names
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_PRICE = "price";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DbHelper(@Nullable Context context,
                           @Nullable String name,
                           @Nullable SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COUNTRY_TABLE = "CREATE TABLE " + TABLE_PRODUCT
                + " ("
                + FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_NAME + " TEXT, "
                + FIELD_PRICE + " DOUBLE " + ")";
        db.execSQL(CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new country
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_NAME, product.getName());
        values.put(FIELD_PRICE, product.getPrice());

        // Inserting Row
        db.insert(TABLE_PRODUCT, null, values);
        db.close(); // Closing database connection
    }

    // Getting single country
    Product getCountry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT, new String[] {
                        FIELD_ID, FIELD_PRICE},
                FIELD_ID + " = ?",
                new String[] { String.valueOf( id ) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        @SuppressLint("Range") Product country = new Product(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("id"))),
                cursor.getString(cursor.getColumnIndex("name")),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex("price"))));
        // return country
        return country;
    }

    // Getting All Product
    @SuppressLint("Range")
    public List getAllProduct() {
        List countryList = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setName(cursor.getString(cursor.getColumnIndex("name")));
                product.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex("price"))));
                // Adding country to list
                countryList.add(product);
            } while (cursor.moveToNext());
        }

        // return country list
        return countryList;
    }

    // Updating single country
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_NAME, product.getName());
        values.put(FIELD_PRICE, product.getPrice());

        // updating row
        return db.update(TABLE_PRODUCT, values, FIELD_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
    }

    // Deleting single country
    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, FIELD_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
        db.close();
    }

    // Deleting all countries
    public void deleteAllProduct() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT,null,null);
        db.close();
    }

    // Getting countries Count
    public int getProductCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
