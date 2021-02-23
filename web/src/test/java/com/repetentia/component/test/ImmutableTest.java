package com.repetentia.component.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableTest extends Thread {
    String value;

    public ImmutableTest(String value) {
        this.value = value;
    }

    @Override
    public void run(){
        value += " world";
        System.out.println(value);
    }
    public static void main(String[] args) {

    }
}


