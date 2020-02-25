package com.test;

import java.io.IOException;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 2020/2/24
 *
 * @author cuirg
 */
public class Test {
    @org.junit.Test
    public void test() throws IOException {
        /*for (int i = 0; i < 10; i++) {
            System.out.println("我是第"+i);
            new Thread(new MyClient("dog")).start();
        }*/
       /* Thread list = new Thread(new MyClient("list"));
        list.start();*/

        ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<String, Integer>();
        map.put("aaa",1);
        map.put("sdfdsf",5);
        map.put("dsfsdfa",4);
        map.put("dsfads",9);
        ConcurrentHashMap<String, Integer> collect = map.entrySet().stream().sorted(Comparator.comparing(ConcurrentHashMap.Entry::getValue)).
                collect(Collectors.toMap(ConcurrentHashMap.Entry::getKey,
                        ConcurrentHashMap.Entry::getValue,(x,y)->(x), ConcurrentHashMap::new));
        System.out.println(map);
        System.out.println(collect);
    }
}
