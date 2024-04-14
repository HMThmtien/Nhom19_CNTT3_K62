package com.app.qlns.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(@Nullable Context context) {
        super(context, "ASM2", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        createUserTable(sqldb);
        createTableHistoryLogin(sqldb);
        createStaffTable(sqldb);
        createProductTable(sqldb);
    }
    void createUserTable(SQLiteDatabase sqldb){
        String[] phanloai = new String[] {
                "CREATE TABLE User (Id INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT, Phone TEXT, Password TEXT, Role TEXT)",
                "INSERT INTO User (Username, Phone, Password, Role) VALUES ('admin', '123', '123', 'admin')"
        };
        for (String query : phanloai) {
            sqldb.execSQL(query);
        }
    }
    void createTableHistoryLogin(SQLiteDatabase sqldb){
        String[] giaoDich = new String[] {
                "CREATE TABLE HistoryLogin (Id INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT,Time TEXT)",
        };
        for (String query : giaoDich) {
            sqldb.execSQL(query);
        }
    }
    void createStaffTable(SQLiteDatabase sqldb){
        String[] phanloai = new String[] {
                "CREATE TABLE Staff (Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Phone TEXT)",
        };
        for (String query : phanloai) {
            sqldb.execSQL(query);
        }
    }
    void createProductTable(SQLiteDatabase sqldb){
        String[] phanloai = new String[] {
                "CREATE TABLE Product (Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Price REAL, Description TEXT, Stock INTEGER)",
        };
        for (String query : phanloai) {
            sqldb.execSQL(query);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PhanLoai");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS GiaoDich");
        onCreate(sqLiteDatabase);
    }

}