package com.example.a317soft.old;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a317soft.bean.User;

import java.util.LinkedList;


public class UserDBHelper extends SQLiteOpenHelper {

    public static final String BD_NAME = "tos_user";

    private static final String CREATE_USER_DB = "create table tos_user ("+
            "id integer primary key autoincrement, " +
            "username text, " +
            "password text)";


    public UserDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //添加用户
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        db.insert(BD_NAME,null,values);
        values.clear();
    }

    //返回所有用户的信息
    @SuppressLint("Range")
    public LinkedList<User> readUsers() {
        LinkedList<User> users = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tos_user",null);
        if(cursor.moveToFirst()) {
            do{
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                users.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    //输入：用户名
    //功能：判断输入的用户名是否已经存在
    //输出：true：不存在重复的用户名     false：存在重复的用户名
    public boolean ifNotExist(String username) {
        LinkedList<User> allUsers = readUsers();
        int size = allUsers.size();
        for(User integ:allUsers){
            if(integ.getUsername().equals(username)) return false;
        }
        return true;
    }

    //输入：用户名
    //功能：根据用户名查找对应的id
    //输出：id
    @SuppressLint("Range")
    public int findId(String username) {
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tos_user where username=?",new String[]{username});
        if(cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }

        return id;
    }

    //更新用户信息
    public boolean updateUser(String username,String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "update tos_user set password=? where username=?";
        String[] obj = new String[]{password,username};
        db.execSQL(sql,obj);
        return true;
    }

}
