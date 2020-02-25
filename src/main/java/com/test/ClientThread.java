package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 2020/2/24
 * 客户端线程
 * @author cuirg
 */
public class ClientThread implements Runnable {
    /* 领养的宠物，操作可以是dog，cat等，list查看排行榜 */
    String operate = "dog";
    public ClientThread(String operate){
        this.operate=operate;
    }

    @Override
    public void run() {
        Socket socket = null;
        PrintWriter os = null;
        BufferedReader is = null;
        try {
            socket = new Socket("127.0.0.1", 9999);
            os = new PrintWriter(socket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //==============写服务端==================//
            os.println(this.operate);
            os.flush();

            //==============读服务端==================//
            System.out.println("服务端反馈:" + is.readLine());


        } catch (Exception e) {
            System.out.println("Error" + e);
        }finally {
            /* 关闭连接 */
            try {
                if(os!=null){
                    os.close();
                    os = null;
                }
                if(is!=null){
                    is.close();
                    is = null;
                }
                if(socket!=null){
                    socket.close();
                    socket = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
