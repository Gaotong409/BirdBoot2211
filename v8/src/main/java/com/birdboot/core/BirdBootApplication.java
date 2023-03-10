package com.birdboot.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 主启动类
 */
public class BirdBootApplication {
    /**
     * ServerSocket用于与客户端建立TCP连接
     */
    private ServerSocket serverSocket;
    /**
     * 构造器用于初始化服务端程序
     */
    public BirdBootApplication(){
        try {
            System.out.println("正在启动服务端...");
            serverSocket = new ServerSocket(8088);
            System.out.println("服务端启动完毕!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 服务端开始工作的方法
     */
    public void start(){
        try {
            while (true) {
                System.out.println("等待客户端连接...");
                Socket socket = serverSocket.accept();
                System.out.println("一个客户端连接了!");

                //启动一个线程处理该客户端的交互
                ClientHandler handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BirdBootApplication application = new BirdBootApplication();
        application.start();
    }

}
