package com.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;

/**
 * 2020/2/24
 *
 * @author cuirg
 */
public class PetServer {

    private volatile static TreeMap<String,Integer> petMap = new TreeMap<String, Integer>();
    public static void main(String args[]) {
        ServerSocket server = null;
        try {
            /* 服务端 */
            server = new ServerSocket(9999);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        while(true){
            try {
                /* 接收客户端 */
                Socket socket = server.accept();
                /* 接收客户端的ip */
                String ip = socket.getInetAddress().toString();
                System.out.println(ip.substring(1)+"连上来了....");
                /* 调用服务端的线程 */
                new ServerThread(socket,petMap);
                //thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
