package com.birdboot.test;

public class TestSingleton {
    public static void main(String[] args) {
        Singletion s1=Singletion.getInstance();
        System.out.println(s1);
        Singletion s2=Singletion.getInstance();
        System.out.println(s2);
    }
}
