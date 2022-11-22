package com.example.a317soft.util;

import android.util.Log;

import com.example.a317soft.bean.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    //添加用户
    public static boolean addUser(User user) {
        String sql = "insert into tos_user(username, password) values('"
                + user.getUsername() + "','" + user.getPassword() + "')";
        Connection connection = DBUtil.getConn();
        Statement statement = null;
        boolean flag = false;
        int res = 0;
        try {
            statement = connection.createStatement();
            res = statement.executeUpdate(sql);
        } catch (Exception e) {
//            Log.e("addUser->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(statement, connection);
        }
        if (res > 0) {
            flag = true;
        }
        return flag;
    }

    //获取所有用户的信息
    public static List<User> readUsers() {
        List<User> list = new ArrayList<>();
        String sql = "select * from tos_user";
        Connection connection = DBUtil.getConn();
        Statement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        int res = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
//            Log.e("readUsers->","readUsers");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                list.add(user);
//                Log.e("readUsers",user.toString());

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

    //判断输入的用户名是否存在
    public static boolean ifNotExist(String username) {
        List<User> allUsers = readUsers();
        int size = allUsers.size();
        for(User integ:allUsers){
            if(integ.getUsername().equals(username)) return false;
        }
        return true;
    }

    //根据用户名查询id
    public static int findId(String username) {
        int id = 0;
        String sql = "select * from tos_user where username='" + username+"'";
        Connection connection = DBUtil.getConn();
        Statement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        int res = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
//            Log.e("findId->","findId");
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (Exception e) {
//            Log.e("findId->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, statement, connection);
        }
        if (res > 0) {
            flag = true;
        }
        return id;
    }

    public static boolean updateUser(String username, String password) {
        String sql = "update tos_user set password='" + password
                + "' where username='" + username+"'";
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
