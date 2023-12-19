package com.yy.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class GameService {


    private static List<String> l1 = new ArrayList<>();
    private static List<String> l2 = new ArrayList<>();

    private static Map<String, String> map1 = new LinkedHashMap<>();
    private static Map<String, String> map2 = new LinkedHashMap<>();

    private final Object lock = new Object();




    public JSONArray test() {
        JSONArray arr1 = new JSONArray();
        JSONArray arr2 = new JSONArray();

        map1.keySet().forEach(k -> arr1.add(map1.get(k)));
        map2.keySet().forEach(k -> arr2.add(map2.get(k)));

        JSONArray arr = new JSONArray();
        arr.add(arr1);
        arr.add(arr2);
        return arr;
    }


    public JSONArray result() {

        JSONArray array = new JSONArray();
        array.add(JSONObject.parseObject(JSONObject.toJSONString(map1)));
        array.add(JSONObject.parseObject(JSONObject.toJSONString(map2)));
        return array;
    }


    public String drawLots(String name, String camp) {
        String identity = null;

        if (camp.equals("blue")) {
            if (map1.keySet().size() == 5)
                return "蓝色方整容已满, 成员: " + map1.keySet();
            identity = map1.get(name);
        }

        if (camp.equals("red")) {
            if (map2.keySet().size() == 5)
                return "红色方整容已满, 成员: " + map2.keySet();
            identity = map2.get(name);
        }


        if (identity == null) {
            synchronized (lock) {
                identity = this.generateIdentity(name, camp);
            }
        }


        StringBuilder str = new StringBuilder();
        str.append("当前蓝色方已确定身份: ").append(map1.size()).append("\n");
        str.append("当前红色方已确定身份: ").append(map2.size()).append("\n");


        str.append("你(").append(name).append(")的身份是-").append(identity).append("\n");

        return str.toString();
    }



    public String generateIdentity(String name, String camp) {
        Collections.shuffle(l1);
        Collections.shuffle(l2);

        Random random = new Random();
        String remove = null;

        if (camp.equals("blue")) {
            if (l1.isEmpty()) return null;
            remove = l1.remove(random.nextInt(l1.size()));
            map1.put(name, remove);
        }

        if (camp.equals("red")) {
            if (l2.isEmpty()) return null;
            remove = l2.remove(random.nextInt(l2.size()));
            map2.put(name, remove);
        }
        return remove;
    }


    @PostConstruct
    public void reset() {
        l1.clear();
        l2.clear();

        l1.add("内");
        l1.add("无");
        l1.add("无");
        l1.add("无");
        l1.add("无");

        l2.add("内");
        l2.add("无");
        l2.add("无");
        l2.add("无");
        l2.add("无");



        map1.clear();
        map2.clear();
    }


}
