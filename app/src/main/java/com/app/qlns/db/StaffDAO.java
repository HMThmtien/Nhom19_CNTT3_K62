package com.app.qlns.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.app.qlns.model.Staff;

public class StaffDAO {
    private Context mContext;
    private SQLiteDatabase myDatabase;

    public StaffDAO(Context mContext) {
        this.mContext = mContext;
        DataBase dbHelper = new DataBase(mContext);
        myDatabase = dbHelper.getWritableDatabase();
    }

    public boolean addStaff(Staff staff) {
        ContentValues values = new ContentValues();
        values.put("Name", staff.getName());
        values.put("Phone", staff.getPhone());
        long result = myDatabase.insert("Staff", null, values);
        return result != -1;
    }
     public   List<Staff> getAllStaff() {
        List<Staff> list = new ArrayList<>();
        Cursor cursor = myDatabase.query("Staff", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            Staff staff = new Staff(id, name, phone);
            list.add(staff);
        }
        cursor.close();
        return list;
    }
    public boolean updateStaff(int id, String name, String phone) {
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Phone", phone);
        String whereClause = "Id=?";
        String[] whereArgs = {String.valueOf(id)};
        long result = myDatabase.update("Staff", values, whereClause, whereArgs);
        return result != -1;
    }
    public  boolean deleteStaff(int id) {
        String whereClause = "Id=?";
        String[] whereArgs = {String.valueOf(id)};
        long result = myDatabase.delete("Staff", whereClause, whereArgs);
        return result != -1;
    }
}