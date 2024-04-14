package com.app.qlns.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.app.qlns.model.Product;

public class ProductDAO {
    private Context mContext;
    private SQLiteDatabase myDatabase;

    public ProductDAO(Context mContext) {
        this.mContext = mContext;
        DataBase dbHelper = new DataBase(mContext);
        myDatabase = dbHelper.getWritableDatabase();
    }

    public boolean addProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("Name", product.getName());
        values.put("Price", product.getPrice());
        values.put("Description", product.getDescription());
        values.put("Stock", product.getStock());
        long result = myDatabase.insert("Product", null, values);
        return result != -1;
    }

    public boolean updateProduct(int id, String name, int price, String description, int stock) {
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Price", price);
        values.put("Description", description);
        values.put("Stock", stock);
        String whereClause = "Id=?";
        String[] whereArgs = {String.valueOf(id)};
        long result = myDatabase.update("Product", values, whereClause, whereArgs);
        return result != -1;
    }

    public boolean deleteProduct(int id) {
        String whereClause = "Id=?";
        String[] whereArgs = {String.valueOf(id)};
        long result = myDatabase.delete("Product", whereClause, whereArgs);
        return result != -1;
    }

   public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        Cursor cursor = myDatabase.query("Product", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            String description = cursor.getString(3);
            int stock = cursor.getInt(4);
            list.add(new Product(id, name, price, description, stock));
        }
        cursor.close();
        return list;
    }
}