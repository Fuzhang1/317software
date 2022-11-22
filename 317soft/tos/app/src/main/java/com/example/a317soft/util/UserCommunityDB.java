package com.example.a317soft.util;

import com.example.a317soft.bean.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserCommunityDB {

    //新增关系
    public static boolean addUserCommunity(int user_id, int community_id) {
        String sql = "insert into tb_user_community(user_id, community_id) values("
                + user_id + "," + community_id + ")";
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

    //删除关系
    public static boolean deleteUserCommunity(int user_id, int community_id) {
        String sql = "delete from tb_user_community where user_id=" + String.valueOf(user_id)
                + " and community_id=" + String.valueOf(community_id);
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

    //根据user_id查询用户已加入的社群的id
    public static List<Integer> queryByUser(int user_id) {
        List<Integer> list = new ArrayList<>();
        String sql = "select * from tb_user_community where user_id=" + String.valueOf(user_id);
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
                int community_id = resultSet.getInt("community_id");
                list.add(community_id);
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

    //根据community_id查询社群中的用户id
    public static  List<Integer> queryByCommunity(int community_id) {
        List<Integer> list = new ArrayList<>();
        String sql = "select * from tb_user_community where community_id=" + String.valueOf(community_id);
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
                int user_id = resultSet.getInt("user_id");
                list.add(community_id);
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
}
