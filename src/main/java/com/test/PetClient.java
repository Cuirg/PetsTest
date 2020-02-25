package com.test;

import java.util.concurrent.TimeUnit;

/**
 * 2020/2/24
 * 调用此类执行客户端
 * @author cuirg
 */
public class PetClient {
    public static void main(String[] args) throws InterruptedException {
        /* 此处模拟1000次领养默认都是狗 */
        for (int i = 0; i < 100; i++) {
            new Thread(new ClientThread("dog")).start();
        }
        /* 此处领养10只猫 */
        for (int i = 0; i <10; i++) {
            new Thread(new ClientThread("cat")).start();
        }
        /* 此处领养30只鸡 */
        for (int i = 0; i < 30; i++) {
            new Thread(new ClientThread("chicken")).start();
        }
        TimeUnit.SECONDS.sleep(2);
        /* 查询排行榜 */
        new Thread(new ClientThread("list")).start();
    }
}
