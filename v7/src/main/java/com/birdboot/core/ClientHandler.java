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
            String path = request.getUri();
            File baseDir = new File(
                    ClientHandler.class.getClassLoader().getResource(".").toURI()
            );
            File staticDir = new File(baseDir, "static");
            /*
                http://localhost:8088/
                path:/
                File file = new File(staticDir,"/");//这里file表示的还是static目录
                file.exists()==>true,虽然存在但它是一个目录，下面文件流读取时是不能读取目录的

                http://localhost:8088/123.html
                path:/123.html
                File file = new File(staticDir,"/123.html");
                file.exists()==>false,表达该文件不存在
             */
            File file = new File(staticDir, path);

            if (file.isFile()) {
                response.setContentFile(file);
            } else {
                response.setStatusCode(404);
                response.setStatusReason("NotFound");
                file = new File(staticDir, "404.html");
                response.setContentFile(file);
            }
            //3发送响应
            response.response();
            /*
                HTTP/1.1 200 OK(CRLF)
                Content-Type: text/html(CRLF)
                Content-Length: 2546(CRLF)(CRLF)
                1011101010101010101......
             */

            //发送状态行

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
