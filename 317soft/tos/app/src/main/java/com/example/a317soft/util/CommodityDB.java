package com.example.a317soft.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.a317soft.bean.Commodity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommodityDB {

    //添加商品
    public static boolean addCommodity(Commodity commodity) {
        String sql = "insert into tos_commodity(user_id, title, description, picture) values(?,?,?,?)";
        Connection connection = DBUtil.getConn();
        PreparedStatement statement = null;
        boolean flag = false;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,commodity.getUser_id());
            statement.setString(2,commodity.getTitle());
            statement.setString(3,commodity.getDescription());
            byte[] picture = commodity.getPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            bitmap = Compress.compressScale(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            picture = baos.toByteArray();
            ByteArrayInputStream pic = new ByteArrayInputStream(picture);
            statement.setBlob(4,pic);
            flag = statement.execute();
        } catch (Exception e) {
//            Log.e("add->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(statement, connection);
        }

        return flag;
    }


    //添加商品
//    public static boolean addCommodity(Commodity commodity) {
////        String sql = "insert into tos_commodity(user_id, title, description, picture) values("
////                + commodity.getUser_id() + ",'" + commodity.getTitle()
////                + "','" + commodity.getDescription() + "',"+ commodity.getPicture() + ")";
//        String sql = "insert into tos_commodity(user_id, title, description, picture) values(?,?,?,?)";
//        Connection connection = DBUtil.getConn();
//        PreparedStatement statement = null;
//        boolean flag = false;
//        int res = 0;
//        try {
//            statement = connection.prepareStatement(sql);
//            statement.setInt(1,commodity.getUser_id());
//            statement.setString(2,commodity.getTitle());
//            statement.setString(3,commodity.getDescription());
//            Blob blob =connection.createBlob();
//            blob.setBytes(1,commodity.getPicture());
//            statement.setBlob(4,blob);
//            res = statement.executeUpdate(sql);
//        } catch (Exception e) {
////            Log.e("add->", e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            DBUtil.close(statement, connection);
//        }
//        if (res > 0) {
//            flag = true;
//        }
//        return flag;
//    }
    //读取自己的商品
    public static List<Commodity> readMyCommodity(Integer user_id) {
        List<Commodity> list = new ArrayList<>();
        String sql = "select * from tos_commodity where user_id=" + String.valueOf(user_id);
        Connection connection = DBUtil.getConn();
        Statement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        int a = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
//            Log.e("readMyCommodity->","readMyCommodity");
            while (resultSet.next()) {
                Commodity commodity = new Commodity();
                commodity.setId(resultSet.getInt(("id")));
                commodity.setUser_id(resultSet.getInt("user_id"));
                commodity.setTitle(resultSet.getString("title"));
                commodity.setDescription(resultSet.getString("description"));
                commodity.setPicture(resultSet.getBytes("picture"));
                list.add(commodity);
//                Log.e("readMyCommodity->",commodity.toString());

            }
        } catch (Exception e) {
//            Log.e("readMyCommodity->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, statement, connection);
        }
        if (a > 0) {
            flag = true;
        }
        return list;
    }

    //根据id查找商品
    public static Commodity findCommodity(Integer id) {
        Commodity commodity = new Commodity();
        String sql = "select * from tos_commodity where id=" + String.valueOf(id);
        Connection connection = DBUtil.getConn();
        Statement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        int res = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
//            Log.e("findCommodity->","findCommodity");
            while (resultSet.next()) {
                commodity.setId(resultSet.getInt(("id")));
                commodity.setUser_id(resultSet.getInt("user_id"));
                commodity.setTitle(resultSet.getString("title"));
                commodity.setDescription(resultSet.getString("description"));
                commodity.setPicture(resultSet.getBytes("picture"));
//                Log.e("findCommodity->",commodity.toString());

            }
        } catch (Exception e) {
//            Log.e("findCommodity->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, statement, connection);
        }
        if (res > 0) {
            flag = true;
        }
        return commodity;
    }

    //下架商品
    public static boolean deleteMyCommodity(Integer id) {
        String sql = "delete from tos_commodity where id=" + String.valueOf(id);
        Connection connection = DBUtil.getConn();
        Statement statement = null;
        boolean flag = false;
        int res = 0;
        try {
            statement = connection.createStatement();
            res = statement.executeUpdate(sql);
        } catch (Exception e) {
//            Log.e("deleteMyCommodity->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(statement, connection);
        }
        if (res > 0) {
            flag = true;
        }
        return flag;
    }
}

