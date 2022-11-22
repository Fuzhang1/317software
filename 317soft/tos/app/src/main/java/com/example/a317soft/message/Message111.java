package com.example.a317soft.message;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a317soft.bean.Commodity;

import java.util.ArrayList;
import java.util.List;

public class Message111 {
    public String intro;
    public String price;

    private static final Message111 me = new Message111();

    private Message111(){
        intro = "";
        price = "";
    }

    public static Message111 getMe() {
        return me;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public String getPrice() {
        return price;
    }


}
