package com.birdboot.core;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

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
            String line=readline();
            System.out.println(line);
            String[] date=line.split("\\s");

            String method =date[0];
            String uri=date[1];
            String protocol=date[2];
            System.out.println("method:"+method);
            System.out.println("uri:"+uri);
            System.out.println("protocol:"+protocol);

            Map<String,String> headers=new HashMap<>();

            while (true){
                line=readline();
                if (line.isEmpty()){
                    break;
                }
                System.out.println("消息头:"+line);
                date=line.split(":\\s");
                headers.put(date[0],date[1]);
            }
            System.out.println("headers:"+headers);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String readline() throws IOException {
        InputStream in=socket.getInputStream();
        int d;
        char pre='a',cur='a';
        StringBuilder builder=new StringBuilder();
        while ((d=in.read())!=-1){
            cur=(char) d;
            if(pre==13 && cur==10){
                break;
            }
            builder.append(cur);
            pre=cur;
        }
        return builder.toString().trim();
    }
}
