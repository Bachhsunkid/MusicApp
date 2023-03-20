package com.example.app_nhac.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.app_nhac.Model.Baihat;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {

    private  static final String DBName = "appnhac.db";

    private  static final String TABLE_USER = "User";
    private  static final String USERNAME = "username";
    private  static final String PASSWORD = "password";

    private  static final String TABLE_BAIHAT = "BaiHat";
    private  static final String IDBAIHAT = "_idbaihat";
    private  static final String TENBAIHAT = "tenbaihat";
    private  static final String CASI = "casi";
    private  static final String LINKBAIHAT = "linkbaihat";
    private  static final String LOIBAIHAT = "loibaihat";

    private  static final String TABLE_USER_BAIHAT = "User_BaiHat";
    private  static final String USERNAME_USER_BAIHAT = "username";
    private  static final String IDBAIHAT_USER_BAIHAT = "idbaihat";
    private  static final String STATUSTHICH = "statusthich";

    private SQLiteDatabase myDB;

    public MyDatabase(@Nullable Context context) {
        super(context, DBName, null, 1);
    }

    @Contract(pure = true)
    public static String getTableUser() {
        return TABLE_USER;
    }

    @Contract(pure = true)
    public static String getUSERNAME() {
        return USERNAME;
    }

    @Contract(pure = true)
    public static String getPASSWORD() {
        return PASSWORD;
    }

    @Contract(pure = true)
    public static String getTableBaihat() {
        return TABLE_BAIHAT;
    }

    @Contract(pure = true)
    public static String getIDBAIHAT() {
        return IDBAIHAT;
    }

    @Contract(pure = true)
    public static String getTENBAIHAT() {
        return TENBAIHAT;
    }

    @Contract(pure = true)
    public static String getCASI() {
        return CASI;
    }

    @Contract(pure = true)
    public static String getLINKBAIHAT() {
        return LINKBAIHAT;
    }

    @Contract(pure = true)
    public static String getLOIBAIHAT() {
        return LOIBAIHAT;
    }

    @Contract(pure = true)
    public static String getTableUserBaihat() {
        return TABLE_USER_BAIHAT;
    }

    @Contract(pure = true)
    public static String getUsernameUserBaihat() {
        return USERNAME_USER_BAIHAT;
    }

    @Contract(pure = true)
    public static String getIdbaihatUserBaihat() {
        return IDBAIHAT_USER_BAIHAT;
    }

    @Contract(pure = true)
    public static String getSTATUSTHICH() {
        return STATUSTHICH;
    }


    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        String queryTableUser = "CREATE TABLE "+TABLE_USER+"(" + USERNAME +" TEXT PRIMARY KEY,"+ PASSWORD +" TEXT NOT NULL"+")";
        String queryTableBaiHat = "CREATE TABLE "+TABLE_BAIHAT+"("
                + IDBAIHAT +" INTEGER PRIMARY KEY,"+ TENBAIHAT +" TEXT NOT NULL,"+ CASI +" TEXT NOT NULL,"+ LINKBAIHAT +" TEXT NOT NULL,"+ LOIBAIHAT +" TEXT NOT NULL "+")";
        String queryTableUser_BaiHat = "CREATE TABLE "+TABLE_USER_BAIHAT+"("
                +  USERNAME_USER_BAIHAT +" TEXT NOT NULL,"+ IDBAIHAT_USER_BAIHAT +" INTEGER NOT NULL,"+ STATUSTHICH +" INTERGER NOT NULL"+")";
        db.execSQL(queryTableUser);
        db.execSQL(queryTableBaiHat);
        db.execSQL(queryTableUser_BaiHat);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void openDB(){
        myDB = getWritableDatabase();
    }
    public   void closeDB(){
        if(myDB != null && myDB.isOpen()){
            myDB.close();
        }
    }
    public long InsertUser(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME,username);
        values.put(PASSWORD,password);
        return db.insert(TABLE_USER,null,values);
    }
    public long UpdateUser(String username,String password){
        ContentValues values = new ContentValues();
        values.put(USERNAME,username);
        values.put(PASSWORD,password);
        String where = USERNAME +" LIKE '" + username+"'";
        return getWritableDatabase().update(TABLE_USER,values,where,null);
    }
    public long DeleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = USERNAME +" LIKE '"+username +"'";
        return db.delete(TABLE_USER,where,null);
    }

    public long InsertBaiHat(int id,String tenbaihat,String casi,String link,String loibaihat){
        ContentValues values = new ContentValues();
        values.put(IDBAIHAT,id);
        values.put(TENBAIHAT,tenbaihat);
        values.put(CASI,casi);
        values.put(LINKBAIHAT,link);
        values.put(LOIBAIHAT,loibaihat);
        return getWritableDatabase().insert(TABLE_BAIHAT,null,values);
    }
    public long DeleteBaiHat(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = IDBAIHAT +" = "+id;
        return db.delete(TABLE_BAIHAT,where,null);
    }
    public long InsertUser_BaiHat(String username,int idbaihat,int statusthich){
        ContentValues values = new ContentValues();
        values.put(USERNAME_USER_BAIHAT,username);
        values.put(IDBAIHAT_USER_BAIHAT,idbaihat);
        values.put(STATUSTHICH,statusthich);
        return getWritableDatabase().insert(TABLE_USER_BAIHAT,null,values);
    }
    public long UpdateUser_BaiHat(String username,int idbaihat,int statusthich){
        ContentValues values = new ContentValues();
        values.put(STATUSTHICH,statusthich);
        String where = USERNAME_USER_BAIHAT +" like '"+username + "' AND "+ IDBAIHAT_USER_BAIHAT+" = "+idbaihat;
        return getWritableDatabase().update(TABLE_USER_BAIHAT,values,where,null);
    }

    public Cursor getAllUser(){
        String query = "SELECT * FROM " + TABLE_USER;
        return getReadableDatabase().rawQuery(query,null);
    }
    public boolean checkUsername(String username){
        String query = "SELECT * FROM " + TABLE_USER +" WHERE "+ USERNAME +" LIKE '"+ username +"'";
        Cursor cursor = myDB.rawQuery(query,null);
        if( cursor.moveToNext()==false) {
            return false;
        }
        else {
            return  true;
        }
    }
    public boolean checkUser(String username,String password){
        String query = "SELECT * FROM " + TABLE_USER +" WHERE "+ USERNAME +" LIKE '"+ username +"' AND "+ PASSWORD + " LIKE '"+ password +"'";
        Cursor cursor = myDB.rawQuery(query,null);
        if( cursor.moveToNext()==false) {
            return false;
        }
        else {
            return  true;
        }
    }

    public Cursor getBaiHat(String username){
        ArrayList<Baihat> mangbaihat = new ArrayList<Baihat>();
        String query = "SELECT "+ IDBAIHAT+","+ TENBAIHAT +","+ CASI +","+ LINKBAIHAT +","+ LOIBAIHAT +
                " FROM " + TABLE_BAIHAT +" JOIN "+TABLE_USER_BAIHAT+" ON "+ IDBAIHAT+" = "+IDBAIHAT_USER_BAIHAT+ " WHERE "+
                USERNAME_USER_BAIHAT +" like '"+username +"'";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
      /*  while (cursor.moveToNext()){
            mangbaihat.add(new Baihat(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
        return mangbaihat;
       */
        return  cursor;
    }

    public Cursor getAllUser_BaiHat(){
        String query = "SELECT * FROM " + TABLE_USER_BAIHAT;
        return getReadableDatabase().rawQuery(query,null);
    }
    public Cursor getLove(){
        String query =  "SELECT * FROM " + TABLE_USER_BAIHAT +" WHERE "+STATUSTHICH+" = "+1;
        return getReadableDatabase().rawQuery(query,null);
    }
    public Cursor getAllBaiHat(){
        String query = "SELECT * FROM " + TABLE_BAIHAT;
        return getReadableDatabase().rawQuery(query,null);
    }
}
