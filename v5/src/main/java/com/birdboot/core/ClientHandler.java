package com.birdboot.core;

import com.birdboot.http.HttpServletRequest;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable{
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run(){
        try {
            HttpServletRequest request=new HttpServletRequest();
            String method = request.getMethod();//通过请求对象获取请求方式
            System.out.println("请求方式:"+method);//GET
            String uri = request.getUri();
            System.out.println("抽象路径:"+uri);

            File baseDir=new File(
                    ClientHandler.class.getClassLoader().getResource(".").toURI()
            );
            File staticDir=new File(baseDir,"static");
            File file=new File(staticDir,"index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
