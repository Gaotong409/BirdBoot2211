package com.birdboot.test;

public class Singletion {
    private static Singletion instance=new Singletion();
    private Singletion(){

    }
    public static Singletion getInstance(){
        return instance;
    }
}
