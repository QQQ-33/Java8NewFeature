package com.tom.yang.redisTest;

import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class TestRedis {
    private static final int limit = 1024 * 1024;
    public static void main(String[] args) throws Exception {
        Jedis jedis = new Jedis("csu-prod-common.tdbkzi.ng.0001.cnn1.cache.amazonaws.com.cn",6379);
        Set<String> keys = jedis.keys("*");
//        System.out.println(keys.size());
//        System.out.println(jedis.get("csu_cache_tsplist"));
        StringBuilder result = new StringBuilder();
        File f = new File("C:\\Users\\qk965\\Desktop\\CSU\\test\\allkeys.csv");
        if(f.exists()){
            f.delete();
        }
        f.createNewFile();
        FileOutputStream out = new FileOutputStream(f, true);
        keys.stream().forEach(k -> {
//            long time = jedis.ttl(k);
//            String value = jedis.get(k);
//            result.append(k).append(",").append(value.getBytes().length).append(",")
//                    .append(k.getBytes().length).append(",")
//                    .append(value.getBytes().length + k.getBytes().length)
//                    .append(",").append(time).append(",").append("\n");
            result.append(k).append(",").append("\n");
            if(result.length() >= limit){
                try {
                    out.write(result.toString().getBytes());
                    result.setLength(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
//        System.out.println(keys.size());
        if(result.length() > 0){
            out.write(result.toString().getBytes());
        }
        out.close();
    }
}
