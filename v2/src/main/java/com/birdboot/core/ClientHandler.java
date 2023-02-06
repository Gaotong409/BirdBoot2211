package com.birdboot.core;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run(){
        try {
            InputStream in=socket.getInputStream();
            int d;
            StringBuilder builder=new StringBuilder();
            char pre='a',cur='a';
            while ((d=in.read())!=-1){
                cur=(char) d;
                if(pre==13 && cur==10){
                    break;
                }
                builder.append(cur);
                pre=cur;
            }
            String line=builder.toString().trim();
            System.out.println(line);
            String[] date=line.split("\\s");

            String method =date[0];
            String uri=date[1];
            String protocol=date[2];
            System.out.println("method:"+method);
            System.out.println("uri:"+uri);
            System.out.println("protocol:"+protocol);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
