package com.example.a317soft.util;

import android.util.Log;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBUtil {

    public static DB_Pool pool = new DB_Pool();

    /*
     * 连接数据库
     * */
    public static Connection getConn() {
        return pool.getConnection();
    }

    public static void close(Statement state, Connection conn) {
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        pool.release(conn);
    }

    public static void close(ResultSet rs, Statement state, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        pool.release(conn);
    }

//    private static String diver = "com.mysql.jdbc.Driver";
//    private static String url = "jdbc:mysql://sh-cynosdbmysql-grp-m9u35qd2.sql.tencentcdb.com:23565/317soft";
//    private static String user = "root";//用户名
//    private static String password = "20020707Fsz";//密码

    /*
     * 连接数据库
     * */
//    public static Connection getConn() {
//        Connection conn = null;
//        try {
//            Class.forName(diver);
//            conn = (Connection) DriverManager.getConnection(url, user, password);//获取连接
//            Log.e("getConn", "连接成功");
//        } catch (ClassNotFoundException e) {
//            Log.e("getConn", e.getMessage(), e);
//            e.printStackTrace();
//        } catch (SQLException e) {
//            Log.e("getConn", e.getMessage(), e);
//            e.printStackTrace();
//        }
//        return conn;
//    }
//
//    public static void close(Statement state, Connection conn) {
//        if (state != null) {
//            try {
//                state.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void close(ResultSet rs, Statement state, Connection conn) {
//        if (rs != null) {
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (state != null) {
//            try {
//                state.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
