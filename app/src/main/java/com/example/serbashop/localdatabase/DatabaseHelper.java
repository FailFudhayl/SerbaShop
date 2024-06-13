package com.example.serbashop.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.serbashop.model.CartItem;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SerbaShop.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_CART = "cart";
    public static final String TABLE_RIWAYAT = "riwayat";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRODUCT_PRICE = "product_price";
    public static final String COLUMN_PRODUCT_IMAGE = "product_image";

    public static final String COLUMN_TRANSACTION_DATE = "transaction_date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableCart = "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_ID + " INTEGER, "
                + COLUMN_PRODUCT_NAME + " TEXT, "
                + COLUMN_PRODUCT_PRICE + " TEXT, "
                + COLUMN_PRODUCT_IMAGE + " TEXT"
                + ")";
        db.execSQL(createTableCart);

        String createTableRiwayat = "CREATE TABLE " + TABLE_RIWAYAT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_ID + " INTEGER, "
                + COLUMN_PRODUCT_NAME + " TEXT, "
                + COLUMN_PRODUCT_PRICE + " TEXT, "
                + COLUMN_PRODUCT_IMAGE + " TEXT, "
                + COLUMN_TRANSACTION_DATE + " TEXT"
                + ")";
        db.execSQL(createTableRiwayat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIWAYAT);
            onCreate(db);
        }
    }
    public void addProductToCart(int productId, String productName, String productPrice, String productImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, productId);
        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_PRODUCT_PRICE, productPrice);
        values.put(COLUMN_PRODUCT_IMAGE, productImage);
        db.insert(TABLE_CART, null, values);
        db.close();
    }

    public Cursor getAllProductsFromCart() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CART, null);
    }

    public long insertCartItem(SQLiteDatabase db, CartItem cartItem) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, cartItem.getProductId());
        values.put(COLUMN_PRODUCT_NAME, cartItem.getProductName());
        values.put(COLUMN_PRODUCT_PRICE, cartItem.getProductPrice());
        values.put(COLUMN_PRODUCT_IMAGE, cartItem.getProductImage());
        return db.insert(TABLE_CART, null, values);
    }

    public Cursor getCartItems(SQLiteDatabase db) {
        return db.query(TABLE_CART, null, null, null, null, null, null);
    }

    public long deleteCartItem(SQLiteDatabase db, int productId) {
        return db.delete("cart", "product_id = ?", new String[]{String.valueOf(productId)});
    }

    // Add method to insert into riwayat
    public long addProductToRiwayat(SQLiteDatabase db, int productId, String productName, String productPrice, String productImage, String transactionDate) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, productId);
        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_PRODUCT_PRICE, productPrice);
        values.put(COLUMN_PRODUCT_IMAGE, productImage);
        values.put(COLUMN_TRANSACTION_DATE, transactionDate);
        return db.insert(TABLE_RIWAYAT, null, values);
    }
}
