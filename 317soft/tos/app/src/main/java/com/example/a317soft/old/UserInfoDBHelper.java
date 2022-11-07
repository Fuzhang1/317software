package com.example.a317soft.old;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a317soft.bean.User;
import com.example.a317soft.bean.UserInfo;

public class UserInfoDBHelper extends SQLiteOpenHelper {

    public static final String BD_NAME = "tos_userInfo";

    private static final String CREATE_USERINFO_DB = "create table tos_userInfo ("+
            "id integer primary key, " +
            "username text, " +
            "QQ text, " +
            "phone text, " +
            "profile blob)";

    public UserInfoDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USERINFO_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addUserInfo(UserInfo userInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", userInfo.getId());
        values.put("username",userInfo.getUsername());
        values.put("QQ",userInfo.getQQ());
        values.put("phone", userInfo.getPhone());
        values.put("profile", userInfo.getProfile());
        db.insert(BD_NAME,null,values);
        values.clear();
    }

    //根据id返回用户信息
    @SuppressLint("Range")
    public UserInfo findUserInfoById(Integer id) {
        UserInfo userInfo = new UserInfo();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tos_userInfo where id=?", new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String QQ = cursor.getString(cursor.getColumnIndex("QQ"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            byte[] profile = cursor.getBlob(cursor.getColumnIndex("profile"));
            userInfo.setId(id);
            userInfo.setUsername(username);
            userInfo.setQQ(QQ);
            userInfo.setPhone(phone);
            userInfo.setProfile(profile);
        }
        cursor.close();
        return userInfo;
    }

    //根据id删除用户信息
    public void deleteUserInfoById(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {
            db.delete(BD_NAME, "id=?", new String[]{String.valueOf(id)});
            db.close();
        }
    }

    //关于更新用户信息，可以通过“先删再增”的方式实现
}
