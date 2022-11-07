package com.example.a317soft.message;

import com.example.a317soft.R;
import com.example.a317soft.bean.Commodity;
import com.example.a317soft.bean.Community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageList {
    //用于存储之前缓存的数据
    public static Map<Integer,List<Community>> comunnitiesMap = new HashMap<>();

    public static Map<Integer,List<Commodity>> commoditiesMap = new HashMap<>();


}
