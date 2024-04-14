package com.app.qlns.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.app.qlns.MySharedPreferences;
import com.app.qlns.model.HistoryLogin;
import com.app.qlns.model.User;

public class UserDAO {
    private final Context mContext;
    private final SQLiteDatabase myDatabase;

    public UserDAO(Context mContext) {
        this.mContext = mContext;
        DataBase dbHelper = new DataBase(mContext);
        myDatabase = dbHelper.getWritableDatabase();
    }
    public boolean registerUser(String username, String phone, String password, String role) {
        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Phone", phone);
        values.put("Password", password);
        values.put("Role", role);

        long result = myDatabase.insert("User", null, values);
        return result != -1;
    }

    // Hàm đăng nhập
    public boolean loginUser(String username, String password) {
        String[] columns = {"Username"};
        String selection = "Username=? AND Password=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = myDatabase.query("User", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        String usernameLogin = null; // Initialize username variable
        if (count > 0) {
            cursor.moveToFirst();
            usernameLogin = cursor.getString(0);
            insertHistoryLogin(usernameLogin, getCurrentTime(), myDatabase);
        }
        if(count > 0){
            MySharedPreferences.saveUserName(mContext, usernameLogin);
        }
        cursor.close(); // Close the cursor after using it
        return count > 0;
    }


    // Hàm xem thông tin tất cả nhân viên
    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM User", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String phone = cursor.getString(2);
                String password = cursor.getString(3);
                String role = cursor.getString(4);

                User user = new User(id, username, phone, password, role);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    // Hàm thêm nhân viên mới
    public boolean addUser(String username, String phone, String password, String role) {
        // Kiểm tra xem số điện thoại đã tồn tại trong cơ sở dữ liệu chưa
        if (checkPhoneExist(phone)) {
            Toast.makeText(mContext, "Số điện thoại đã tồn tại!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra xem tên người dùng đã tồn tại trong cơ sở dữ liệu chưa
        if (checkUsernameExist(username)) {
            Toast.makeText(mContext, "Tên người dùng đã tồn tại!", Toast.LENGTH_SHORT).show();
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Phone", phone);
        values.put("Password", password);
        values.put("Role", role);

        long result = myDatabase.insert("User", null, values);
        return result != -1;
    }

    private boolean checkPhoneExist(String phone) {
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM User WHERE Phone=?", new String[]{phone});
        return cursor.getCount() > 0;
    }

    private boolean checkUsernameExist(String username) {
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM User WHERE Username=?", new String[]{username});
        return cursor.getCount() > 0;
    }


    // Hàm xoá nhân viên
    public boolean deleteUser(int userId) {
        String whereClause = "Id=?";
        String[] whereArgs = {String.valueOf(userId)};

        int result = myDatabase.delete("User", whereClause, whereArgs);
        return result > 0;
    }

    // Hàm sửa thông tin nhân viên
    public boolean updateUser(int userId, String username, String phone, String password, String role) {
        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Phone", phone);
        values.put("Password", password);
        values.put("Role", role);

        String whereClause = "Id=?";
        String[] whereArgs = {String.valueOf(userId)};

        int result = myDatabase.update("User", values, whereClause, whereArgs);
        return result > 0;
    }
    void insertHistoryLogin(String username, String time, SQLiteDatabase sqldb) {
        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Time", time);
        sqldb.insert("HistoryLogin", null, values);
    }
    // Method to get current time in desired format
    String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    public ArrayList<HistoryLogin> getAllHistoryLogin() {
        ArrayList<HistoryLogin> historyLoginList = new ArrayList<>();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM HistoryLogin", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String time = cursor.getString(2);
                historyLoginList.add(new HistoryLogin(id, username, time));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return historyLoginList;
    }
}