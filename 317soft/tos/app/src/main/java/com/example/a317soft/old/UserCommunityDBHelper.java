package com.example.a317soft.old;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserCommunityDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "tb_user_community";

    private static final String CREATE_DB = "create table tb_user_community(" +
            "user_id integer," +
            "community_id integer)";


    public UserCommunityDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //新增关系
    public boolean addUserCommunity(int user_id, int community_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", user_id);
        values.put("community_id", community_id);
        db.insert(DB_NAME,null, values);
        values.clear();
        return true;
    }

    //删除关系
    public int deleteUserCommunity(String user_id, String community_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DB_NAME,"user_id = ? and community_id = ?",new String[]{user_id, community_id});
    }



    //根据user_id查询,返回community_id的List
    @SuppressLint("Range")
    public List<Integer> queryByUser(int user_id) {
        List<Integer> communityIdList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_NAME,null,"user_id = ?",new String[]{String.valueOf(user_id)},null,null,null);
        if(cursor != null) {
            while(cursor.moveToNext()) {
                int community_id = cursor.getInt(cursor.getColumnIndex("community_id"));
                communityIdList.add(community_id);
            }
            cursor.close();
        }
        return communityIdList;
    }



    //根据community_id查询,返回user_id的List
    @SuppressLint("Range")
    public List<Integer> queryByCommunity(int community_id) {
        List<Integer> userIdList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_NAME,null,"community_id = ?",new String[]{String.valueOf(community_id)},null,null,null);
        if(cursor != null) {
            while(cursor.moveToNext()) {
                int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
                userIdList.add(user_id);
            }
            cursor.close();
        }
        return userIdList;
    }
}