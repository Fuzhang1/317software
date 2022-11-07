package com.example.a317soft.util;

import com.example.a317soft.bean.Commodity;
import com.example.a317soft.bean.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostDB {

    //发布帖子
    public static boolean addPost(Post post) {
        String sql = "insert into tos_Post(user_id, commodity_id, community_id, description, price) values("
                + post.getUser_id() + "," + post.getCommodity_id() + ","
                + post.getCommunity_id() + ",'" + post.getDescription() + "','"
                + post.getPrice() + "')";
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

    //查看自己的帖子
    public static List<Post> readMyPosts(Integer user_id) {
        List<Post> list = new ArrayList<>();
        String sql = "select * from tos_post where user_id=" + String.valueOf(user_id);
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
                Post post = new Post();
                post.setId(resultSet.getInt(("id")));
                post.setUser_id(resultSet.getInt("user_id"));
                post.setCommodity_id(resultSet.getInt("commodity_id"));
                post.setCommunity_id(resultSet.getInt("community_id"));
                post.setDescription(resultSet.getString("description"));
                post.setPrice(resultSet.getString("price"));
                list.add(post);
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

    //查看社群中的帖子
    public static List<Post> postsByCommunity(Integer community_id) {
        List<Post> list = new ArrayList<>();
        String sql = "select * from tos_post where community_id=" + String.valueOf(community_id);
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
                Post post = new Post();
                post.setId(resultSet.getInt(("id")));
                post.setUser_id(resultSet.getInt("user_id"));
                post.setCommodity_id(resultSet.getInt("commodity_id"));
                post.setCommunity_id(resultSet.getInt("community_id"));
                post.setDescription(resultSet.getString("description"));
                post.setPrice(resultSet.getString("price"));
                list.add(post);
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

    public static List<Post> postsByDate(List<Integer> community_id) {
        List<Post> list = new ArrayList<>();
        int cnt = 10;
        for(int i=0;i<community_id.size();i++){
            String sql = "select * from tos_post where community_id=" + String.valueOf(community_id.get(i));
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
                    Post post = new Post();
                    post.setId(resultSet.getInt(("id")));
                    post.setUser_id(resultSet.getInt("user_id"));
                    post.setCommodity_id(resultSet.getInt("commodity_id"));
                    post.setCommunity_id(resultSet.getInt("community_id"));
                    post.setDescription(resultSet.getString("description"));
                    post.setPrice(resultSet.getString("price"));
                    list.add(post);
                    cnt--;
                    if(cnt<=0) break;
//                Log.e("readMyCommodity->",commodity.toString());
                }
            } catch (Exception e) {
//            Log.e("readMyCommodity->", e.getMessage(), e);
                e.printStackTrace();
            } finally {
                DBUtil.close(resultSet, statement, connection);
            }
            if(cnt<=0) break;
        }
        return list;
    }

    public static boolean deleteMyPost(Integer id) {
        String sql = "delete from tos_post where commodity_id=" + String.valueOf(id);
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

    public static Post findPostById(Integer id) {
        Post post = new Post();
        String sql = "select * from tos_post where id=" + String.valueOf(id);
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
                post.setId(resultSet.getInt(("id")));
                post.setUser_id(resultSet.getInt("user_id"));
                post.setCommodity_id(resultSet.getInt("commodity_id"));
                post.setCommunity_id(resultSet.getInt("community_id"));
                post.setDescription(resultSet.getString("description"));
                post.setPrice(resultSet.getString("price"));
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
        return post;
    }

    public static boolean deletePostByQuit(int user_id, int community_id) {
        String sql = "delete from tos_post where user_id=" + String.valueOf(user_id)
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

}
