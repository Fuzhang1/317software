package com.example.a317soft.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.a317soft.bean.UserInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserInfoDB {

    //添加用户信息
    public static boolean addUserInfo(UserInfo userInfo) {
        String sql = "insert into tos_userInfo(id, username, QQ, phone, profile) values(?,?,?,?,?)";
        Connection connection = DBUtil.getConn();
        PreparedStatement statement = null;
        boolean flag = false;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,userInfo.getId());
            statement.setString(2,userInfo.getUsername());
            statement.setString(3,userInfo.getQQ());
            statement.setString(4,userInfo.getPhone());
            byte[] picture = userInfo.getProfile();
            Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            bitmap = Compress.compressScale(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            picture = baos.toByteArray();
            ByteArrayInputStream pic = new ByteArrayInputStream(picture);
            statement.setBlob(5,pic);
            flag = statement.execute();
        } catch (Exception e) {
//            Log.e("addUser->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(statement, connection);
        }

        return flag;
    }

    //根据id返回用户信息
    public static UserInfo findUserInfoById(Integer id) {
        UserInfo userInfo = new UserInfo();
        String sql = "select * from tos_userInfo where id=" + String.valueOf(id);
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
                userInfo.setId(resultSet.getInt(("id")));
                userInfo.setUsername(resultSet.getString("username"));
                userInfo.setQQ(resultSet.getString("QQ"));
                userInfo.setPhone(resultSet.getString("Phone"));
                userInfo.setProfile(resultSet.getBytes("profile"));
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
        return userInfo;
    }

    //根据id删除用户信息
    public static boolean deleteUserInfoById(Integer id) {
        String sql = "delete from tos_userInfo where id=" + String.valueOf(id);
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

    //更新用户信息（只允许修改QQ、phone、profile）
    public static boolean updateUserInfo(UserInfo userInfo) {
        String sql = "update tos_userInfo set QQ=" + "'" + userInfo.getQQ() + "'"
                + ",phone=" + "'" + userInfo.getPhone() + "'"
                + ",profile=" + "'" +userInfo.getProfile() + "'"
                + "where id=" + userInfo.getId();
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
