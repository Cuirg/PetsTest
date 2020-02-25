package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 * 2020/2/24
 * 服务端线程类
 * @author cuirg
 */

public class ServerThread extends Thread {
    Socket socket;
    BufferedReader br = null;
    PrintWriter os = null;
    TreeMap<String, Integer> petMap;

    public ServerThread(Socket socket, TreeMap<String, Integer> petMap) {
        this.socket = socket;
        this.petMap = petMap;
        start(); //启动线程
    }

    public void run() {
        BufferedReader is = null;
        PrintWriter os = null;

        try {
            boolean flag = true;
            System.out.println("server listen on port 9999...");
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new PrintWriter(socket.getOutputStream());

            while (flag) {
                System.out.println("-------------------");
                //多线程
                //new ServerThread(socket, clientnum).start();


                //标准输入端读取一行
                BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
                String str;
                /* 操作map加锁 */
                synchronized (ServerThread.class) {
                    while (!"".equals(str = is.readLine())) {
                        System.out.println(".....start...........");
                        if (("list").equalsIgnoreCase(str)) {
                            System.out.println("用户查询了一次排行榜");
                            List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(petMap.entrySet());

                            Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
                                //排序
                                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                                    return o2.getValue().compareTo(o1.getValue());
                                }
                            });
                            os.println("服务端已经收到请求list：" + list);
                        } else {
                            /* 宠物数量+1 */
                            if (petMap.containsKey(str)) {
                                int count = petMap.get(str);
                                count++;
                                petMap.put(str, count);

                                System.out.println(Thread.currentThread().getName() + "领养了一只" + str + ",共" + petMap.get(str) + "只");

                            } else {
                                System.out.println("领养了一只" + str);
                                petMap.put(str, 1);
                            }
                            os.println("服务端已经收到：OK");
                        }
                        //====向客户端反馈读到的信息=====//

                        os.flush();
                        System.out.println("......end.........");
                    }
                }

            }

            //****流如果关闭，socket也将会关闭，所以如果想在一个socket连接之内通讯*****//，
            //***则应该在while之外关闭socket****//

            //server.close();

        } catch (Exception e) {
            System.out.println("Error" + e);
        } finally {
            try {
                is.close();
                os.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

