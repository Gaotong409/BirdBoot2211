package com.birdboot.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象
 * 该类的每一个实例用于表示浏览器发送过来的一个请求
 * HTTP协议要求一个请求由三个部分构成:
 * 1:请求行
 * 2:消息头
 * 3:消息正文
 */
public class HttpServletRequest {
    private Socket socket;
    //请求行相关信息
    private String method;//请求方式
    private String uri;//抽象路径
    private String protocol;//协议版本

    //消息头相关信息
    //key:消息头的名字  value:消息头对应的值
    private Map<String,String> headers = new HashMap<>();



    public HttpServletRequest(Socket socket) throws IOException {
        this.socket = socket;
        //读取请求行
        parseRequestLine();
        //读取消息头
        parseHeaders();
        //读取消息正文
        parseContent();

    }
    //解析请求行
    private void parseRequestLine() throws IOException {
        String line = readLine();
        System.out.println(line);
        //将请求行按照空格拆分为三部分
        String[] data = line.split("\\s");
        method = data[0];//请求方式
        uri = data[1];//抽象路径
        protocol = data[2];//协议版本
        System.out.println("method:"+method);
        System.out.println("uri:"+uri);
        System.out.println("protocol:"+protocol);
    }
    //解析消息头
    private void parseHeaders() throws IOException {
        while(true) {
            String line = readLine();
            if(line.isEmpty()){//如果读取到了空行,则说明消息头部分读取完毕了
                break;
            }
            System.out.println("消息头:" + line);
            //将每个消息头按照": "进行拆分，将消息头的名字作为key，值作为value保存到headers中
            String[] data = line.split(":\\s");
            headers.put(data[0],data[1]);
        }
        System.out.println("headers:"+headers);
    }
    //解析消息正文
    private void parseContent(){

    }


    /**
     * 被重用的方法,读取来自浏览器发送过来的一行字符串
     * (解析请求行和消息头时会复用这个方法的操作)
     * @return
     * @throws IOException
     */
    private String readLine() throws IOException {
        //当socket对象没有发生过改变的前提下,无论调用多少次它的获取输入流操作始终返回的都是同一条输入流(获取输出流也是如此)
        InputStream in = socket.getInputStream();
        int d;
        //pre记录上次读取的字符,cur记录本次读取的字符
        char pre='a',cur='a';
        StringBuilder builder = new StringBuilder();
        while((d = in.read())!=-1){
            cur = (char)d;//本次读取的字符
            if(pre==13 && cur==10){//如果上次读取的字符是回车符并且本次读取的是换行符则应当停止读取
                break;
            }
            builder.append(cur);
            pre = cur;//在读取下一个字符前将本次读取的字符记做上次读取的字符
        }
        return builder.toString().trim();
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

    /**
     * 根据消息头的名字获取对应消息头的值
     * @param name
     * @return
     */
    public String getHeader(String name) {
        /*
            由于headers这个Map中之前解析时将消息头的名字做为key，消息头的值作为value保存的。
            因此这里只需要将参数传入的消息头的名字作为key从headers中提取value返回即可
         */
        return headers.get(name);
    }
}





