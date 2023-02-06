package com.birdboot.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServletRequest {
    private Socket socket;
    private String method;
    private String uri;
    private String protocol;

    private Map<String,String>headers=new HashMap<>();

    public HttpServletRequest() throws IOException {
        this.socket=socket;
    }
    private void parseRequestLine() throws IOException {
        String line=readline();
        System.out.println(line);
        String[] date=line.split("\\s");

        method =date[0];
        uri=date[1];
        protocol=date[2];
        System.out.println("method:"+method);
        System.out.println("uri:"+uri);
        System.out.println("protocol:"+protocol);

    }
    private void parseHeaders() throws IOException {
        while (true){
            String line=readline();
            if (line.isEmpty()){
                break;
            }
            System.out.println("消息头:"+line);
            String[] date=line.split(":\\s");
            headers.put(date[0],date[1]);
        }
        System.out.println("headers:"+headers);
    }
    private void parseContent(){

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

    public Socket getSocket() {
        return socket;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHeaders(String name) {
        return headers.get(name);
    }
}


