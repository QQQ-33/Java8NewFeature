package com.tom.yang.redisTest;

import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;

public class TestRedis2 {
    public static void main(String[] args) {
//        Jedis jedis = new Jedis("csu-prod-common.tdbkzi.ng.0001.cnn1.cache.amazonaws.com.cn",6379);
        Jedis jedis = new Jedis("csu-pv-common.fqv0uc.0001.cnn1.cache.amazonaws.com.cn",6379);
        Set<String> keys = jedis.keys("csu_cache_block_*");
        System.out.println(keys.size());
//        List<String> list = new ArrayList<>();
//        keys.stream().forEach( key -> {
//            String type = jedis.type(key);
//            if("zset".equals(type)){
//                String path = (String)(jedis.zrange(key, 0, 1).toArray()[0]);
//                list.add( getDate(path) );
//            }
//        });
//
//        Map<String, List<String>> map = list.stream().collect(Collectors.groupingBy(i -> i));
//        map.forEach((k, v) -> {
//            System.out.println("Date: "+ k + " Count: " + v.size());
//        });

//        String path = (String)(jedis.zrange("0BFBEFE6000526B0EF9A", 0, 1).toArray()[0]);
//        System.out.println(path);
//        System.out.println(jedis.ttl("0BFBEFE6000526B0EF9A"));
//        System.out.println(jedis.type("0BFBEFE6000526B0EF9A"));

//        String p = "s3://da-csu-global-china-efpa-efpabucket-1p3jekxlo4n99/EFPAReceived/BC601L3180838-FATSP-20200826-072100002-0BFBEFE6000526B0EF9A.txt";
//        System.out.println(getDate(p));
    }

    private static String getDate(String path){
        return path.split("/")[4].split("-")[2];
    }
}
