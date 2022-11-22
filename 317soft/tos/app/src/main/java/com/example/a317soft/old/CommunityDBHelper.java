package com.example.a317soft.old;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a317soft.bean.Community;

import java.util.ArrayList;
import java.util.List;

public class CommunityDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "tb_community";

    private static final String CREATE_COMMUNITY_DB = "create table tb_community(" +
            "id integer primary key autoincrement," +
            "title text," +
            "description text," +
            "picture blob," +
            "memberCount integer)";


    public CommunityDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COMMUNITY_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //添加社群
    public boolean addCommunity(Community community) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",community.getTitle());
        values.put("description",community.getDescription());
        values.put("picture",community.getPicture());
        values.put("memberCount",community.getMemberCount());
        db.insert(DB_NAME,null,values);
        values.clear();
        return true;
    }

    //按照社群名字模糊查询社群
    @SuppressLint("Range")
    public List<Community> queryByTitle(String search_title) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Community> communityList = new ArrayList<>();
        Cursor cursor = db.query(DB_NAME, null, "title like ?", new String[]{"%"+search_title+"%"}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                byte[] picture = cursor.getBlob(cursor.getColumnIndex("picture"));
                int memberCount = cursor.getInt(cursor.getColumnIndex("memberCount"));

                Community community = new Community();
                community.setId(id);
                community.setTitle(title);
                community.setDescription(description);
                community.setPicture(picture);
                community.setMemberCount(memberCount);

                communityList.add(community);
            }
            cursor.close();
        }
        else
        {
            for(int i = 0; i < 10; i++) {
                communityList.add(queryById(i));
            }
        }
        return communityList;
    }

    //按照社群id查询社群
    @SuppressLint("Range")
    public Community queryById(int search_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_NAME, null, "id = ?", new String[]{String.valueOf(search_id)}, null, null, null);
        if(cursor == null)
            return new Community();
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String description = cursor.getString(cursor.getColumnIndex("description"));
        byte[] picture = cursor.getBlob(cursor.getColumnIndex("picture"));
        int memberCount = cursor.getInt(cursor.getColumnIndex("memberCount"));

        Community community = new Community();
        community.setId(id);
        community.setTitle(title);
        community.setDescription(description);
        community.setPicture(picture);
        community.setMemberCount(memberCount);

        return community;
    }

    //按照社群名字精确查询社群id
    @SuppressLint("Range")
    public int searchIdByTitle(String search_title) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Community> communityList = new ArrayList<>();
        Cursor cursor = db.query(DB_NAME, null, "title = ?", new String[]{search_title}, null, null, null);
        if(cursor.getCount() == 0)
            return -1;
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        return id;
    }

    //点击加入社群后，输入社群id，社群人数加1
    public boolean updateMemberCount(int comm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Community community = queryById(comm_id);
        values.put("title",community.getTitle());
        values.put("description",community.getDescription());
        values.put("picture",community.getPicture());
        values.put("memberCount",community.getMemberCount()+1);
        db.update(DB_NAME,values,"id = ?",new String[]{String.valueOf(comm_id)});
        values.clear();
        return true;
    }

}