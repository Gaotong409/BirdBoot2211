package com.birdboot.core;

import com.birdboot.http.HttpServletRequest;
import com.birdboot.http.HttpServletResponse;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 该线程负责与指定的客户端进行HTTP交互
 * HTTP协议要求客户端与服务端的交互规则必须遵守一问一答的原则。
 * 因此，服务端这里交互主流程:
 * 1:解析请求
 * 2:处理请求
 * 3:发送响应
 * 处理后与客户端断开链接
 */
public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //1解析请求
            HttpServletRequest request = new HttpServletRequest(socket);
            HttpServletResponse response=new HttpServletResponse(socket);
            //2处理请求
           DispatcherServlet.getInstance().service(request,response);
            //3发送响应
            response.response();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
