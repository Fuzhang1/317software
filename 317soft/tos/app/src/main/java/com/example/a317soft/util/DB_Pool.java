package com.example.a317soft.util;

import android.util.Log;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class DB_Pool implements DataSource {
    //定义集合容器，用于保存多个数据库连接对象
    final static int max = 10;
    private static List<Connection> pool = Collections.synchronizedList(new ArrayList<Connection>());
    //利用线程初始化与数据库的连接，并且启动定时任务轮询连接是否有效
     public static void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < max; i++) {
                    Connection con = DB_Con.getConn();
                    pool.add(con);
                }
            }
        }).start();
         TimerTask timerTask = new TimerTask() {
             @Override
             public void run() {
                check();
             }
         };
         new Timer().schedule(timerTask,1000000,1000000);

    }

    public static void check(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 List<Connection> old_pool = pool;
                 List<Connection> new_pool = Collections.synchronizedList(new ArrayList<Connection>());
                 for (int i = 0; i < max/2; i++) {
                     Log.wtf("3","新建连接:" + pool.size());
                     Connection con = DB_Con.getConn();
                     new_pool.add(con);
                 }
                 pool = new_pool;
                 for(int i = 0;i<max;i++){
                     try {
                         Log.wtf("4","释放连接:" + pool.size());
                         old_pool.get(i).close();
                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                 }
                 old_pool = null;
                 for(int i = pool.size();i<max;i++){
                     Connection con = DB_Con.getConn();
                     new_pool.add(con);
                 }
             }
         }).start();

    }

    //返回连接池的大小
    public int getSize() {
        return pool.size();
    }

    public void release(Connection con){
        Log.wtf("2","release:" + pool.size());
        if(pool.size()>max){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            pool.add(con);
        }

    }

    @Override
    public Connection getConnection(){
        Log.wtf("1","get:" + pool.size());
        if(pool.size()>0){
            return  pool.remove(0);
        }
        else{
            return  DB_Con.getConn();
        }
    }

    @Override
    public Connection getConnection(String s, String s1) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int i) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
