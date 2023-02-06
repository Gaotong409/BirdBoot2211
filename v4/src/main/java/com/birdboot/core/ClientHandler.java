package com.birdboot.core;

import com.birdboot.http.HttpServletRequest;
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
            HttpServletRequest request=new HttpServletRequest();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
