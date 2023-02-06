package com.birdboot.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class HttpServletResponse {
    private Socket socket;
    private int statusCode = 200;
    private String statusReason = "OK";

    private File contentFile;

    public HttpServletResponse(Socket socket) {
        this.socket = socket;
    }

    public void response() throws IOException {
        println("HTTP/1.1 " + statusCode + " " + statusReason);
        sendStatusLine();
        sendHeaders();
        sendContent();
        //发送响应头
        //单独发送回车+换行表达项英头部分发送完毕
        //发送响应正文(index.html页面内容)

    }
    private void sendStatusLine() throws IOException {
        println("Content-Type: text/html");
        println("Content-Length: " + contentFile.length());
    }
    private void sendHeaders() throws IOException {
        println("");
    }
    private void sendContent() throws IOException {
        OutputStream out = socket.getOutputStream();
        FileInputStream fis = new FileInputStream(contentFile);
        int len = 0;
        byte[] buf = new byte[1024 * 10];
        while ((len = fis.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    }
        private void println (String line) throws IOException {
            OutputStream out = socket.getOutputStream();
            byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
            out.write(data);
            out.write(13);//发送回车符
            out.write(10);//发送换行符
        }
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public File getContentFile() {
        return contentFile;
    }

    public void setContentFile(File contentFile) {
        this.contentFile = contentFile;
    }
}
