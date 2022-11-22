package com.example.a317soft.old;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a317soft.bean.Post;

import java.util.ArrayList;
import java.util.List;


public class PostDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "tos_post";

    private static final String CREATE_POST_DB = "create table tos_post(" +
            "id integer primary key autoincrement," +
            "user_id integer," +
            "commodity_id integer," +
            "community_id integer," +
            "description text," +
            "price text)";

    public PostDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_POST_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id",post.getUser_id());
        values.put("commodity_id",post.getCommodity_id());
        values.put("community_id", post.getCommunity_id());
        values.put("description",post.getDescription());
        values.put("price",post.getPrice());
        db.insert(DB_NAME,null,values);
        values.clear();
        return true;
    }

    @SuppressLint("Range")
    public List<Post> readMyPosts(Integer user_id) {
        List<Post> myPosts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tos_post where user_id=?",new String[]{String.valueOf(user_id)});
        if(cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                Integer commodity_id = cursor.getInt(cursor.getColumnIndex("commodity_id"));
                Integer community_id = cursor.getInt(cursor.getColumnIndex("community_id"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                Post post = new Post();
                post.setId(id);
                post.setUser_id(user_id);
                post.setCommodity_id(commodity_id);
                post.setCommunity_id(community_id);
                post.setDescription(description);
                post.setPrice(price);
                myPosts.add(post);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return myPosts;
    }

    @SuppressLint("Range")
    public List<Post> postsByCommunity(Integer community_id) {
        List<Post> myPosts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tos_post where user_id=?",new String[]{String.valueOf(community_id)});
        if(cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                Integer user_id = cursor.getInt((cursor.getColumnIndex("user_id")));
                Integer commodity_id = cursor.getInt(cursor.getColumnIndex("commodity_id"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                Post post = new Post();
                post.setId(id);
                post.setUser_id(user_id);
                post.setCommodity_id(commodity_id);
                post.setCommunity_id(community_id);
                post.setDescription(description);
                post.setPrice(price);
                myPosts.add(post);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return myPosts;
    }

    @SuppressLint("Range")
    public List<Post> postsByDate() {
        List<Post> myPosts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tos_post", null);
        int cnt = 10;
        if(cursor.moveToLast()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                Integer user_id = cursor.getInt((cursor.getColumnIndex("user_id")));
                Integer commodity_id = cursor.getInt(cursor.getColumnIndex("commodity_id"));
                Integer community_id = cursor.getInt(cursor.getColumnIndex("community_id"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                Post post = new Post();
                post.setId(id);
                post.setUser_id(user_id);
                post.setCommodity_id(commodity_id);
                post.setCommunity_id(community_id);
                post.setDescription(description);
                post.setPrice(price);
                myPosts.add(post);
                cnt--;
            }while (cursor.moveToPrevious()&&(cnt>0));
        }
        cursor.close();
        return myPosts;
    }

    public void deleteMyPost(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {
            db.delete(DB_NAME,"id=?",new String[]{String.valueOf(id)});
            db.close();
        }
    }


    @SuppressLint("Range")
    public Post findPostById(Integer id) {
        Post post = new Post();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tos_post where id=?",new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()) {
            Integer user_id = cursor.getInt((cursor.getColumnIndex("user_id")));
            Integer commodity_id = cursor.getInt(cursor.getColumnIndex("commodity_id"));
            Integer community_id = cursor.getInt(cursor.getColumnIndex("community_id"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            post.setId(id);
            post.setUser_id(user_id);
            post.setCommodity_id(commodity_id);
            post.setCommunity_id(community_id);
            post.setDescription(description);
            post.setPrice(price);
        }
        return post;
    }
}
