package com.example.a317soft.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.a317soft.bean.Community;
import com.example.a317soft.bean.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommunityDB {

    public static boolean addCommunity(Community community) {
        String sql = "insert into tb_community(title, description, picture, memberCount) values(?,?,?,?)";
        Connection connection = DBUtil.getConn();
        PreparedStatement statement = null;
        boolean flag = false;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,community.getTitle());
            statement.setString(2,community.getDescription());
            byte[] picture = community.getPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            bitmap = Compress.compressScale(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            picture = baos.toByteArray();
            ByteArrayInputStream pic = new ByteArrayInputStream(picture);
            statement.setBlob(3,pic);
            statement.setInt(4,community.getMemberCount());
            flag = statement.execute();
        } catch (Exception e) {
//            Log.e("addCommunity->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(statement, connection);
        }

        return flag;
    }

    //按照社群名字模糊查询社群
    public static List<Community> queryByTitle(String title) {
        List<Community> list = new ArrayList<>();
        String sql = "select * from tb_community where title like" + "'%" + title + "%'";
        Connection connection = DBUtil.getConn();
        Statement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        int res = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
//            Log.e("queryByTitle->","queryByTitle");
            while (resultSet.next()) {
                Community community = new Community();
                community.setId(resultSet.getInt("id"));
                community.setTitle(resultSet.getString("title"));
                community.setDescription(resultSet.getString("description"));
                community.setPicture(resultSet.getBytes("picture"));
                community.setMemberCount(resultSet.getInt("memberCount"));
                list.add(community);
//                Log.e("queryByTitle",community.toString());

            }
        } catch (Exception e) {
//            Log.e("readUsers->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, statement, connection);
        }
        if (res > 0) {
            flag = true;
        }
        return list;
    }

    //根据社群名字精确查找社群
    public static int searchByTitle(String title) {
        int id = -1;
        String sql = "select * from tb_community where title ='" + title+"'";
        Connection connection = DBUtil.getConn();
        Statement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        int res = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
//            Log.e("queryByTitle->","queryByTitle");
            while (resultSet.next()) {
                id = resultSet.getInt("id");
//                Log.e("queryByTitle",community.toString());
            }
        } catch (Exception e) {
//            Log.e("readUsers->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, statement, connection);
        }
        if (res > 0) {
            flag = true;
        }
        return id;
    }

    //按照社群id查找社群
    public static Community queryById(int id) {
        Community community = new Community();
        String sql = "select * from tb_community where id =" + String.valueOf(id);
        Connection connection = DBUtil.getConn();
        Statement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        int res = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
//            Log.e("queryByTitle->","queryByTitle");
            while (resultSet.next()) {
                community.setId(resultSet.getInt("id"));
                community.setTitle(resultSet.getString("title"));
                community.setDescription(resultSet.getString("description"));
                community.setPicture(resultSet.getBytes("picture"));
                community.setMemberCount(resultSet.getInt("memberCount"));
//                Log.e("queryByTitle",community.toString());
            }
        } catch (Exception e) {
//            Log.e("readUsers->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, statement, connection);
        }
        if (res > 0) {
            flag = true;
        }
        return community;
    }

    //点击加入社群后，输入社群id，社群人数增加
    public static boolean updateMemberCount(int id) {
        Community community = queryById(id);
        String sql = "update tb_community set memberCount=" + String.valueOf(community.getMemberCount()+1)
                + " where id=" + String.valueOf(id);
        Connection connection = DBUtil.getConn();
        Statement statement = null;
        boolean flag = false;
        int res = 0;
        try {
            statement = connection.createStatement();
            res = statement.executeUpdate(sql);
        } catch (Exception e) {
//            Log.e("updateUser->", e.getMessage(), e);
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
