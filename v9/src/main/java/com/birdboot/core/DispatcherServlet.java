package com.birdboot.core;

import com.birdboot.http.HttpServletRequest;
import com.birdboot.http.HttpServletResponse;

import java.io.File;
import java.net.URISyntaxException;

public class DispatcherServlet {
    private static DispatcherServlet instance=new DispatcherServlet();
    private DispatcherServlet(){

    }
    public static DispatcherServlet getInstance(){
        return instance;
    }

    private static File baseDir;
    private static File staticDir;
    static {
        try {
            File baseDir = new File(
                    ClientHandler.class.getClassLoader().getResource(".").toURI()
            );
            File staticDir = new File(baseDir, "static");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void service(HttpServletRequest request,HttpServletResponse response){
        String path = request.getUri();


        File file = new File(staticDir, path);

        if (file.isFile()) {
            response.setContentFile(file);
        } else {
            response.setStatusCode(404);
            response.setStatusReason("NotFound");
            file = new File(staticDir, "404.html");
            response.setContentFile(file);
        }
    }
}
