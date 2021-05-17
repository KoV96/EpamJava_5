package com.epam.rd.java.basic.practice5;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public final class Part2 {

    private static final InputStream STD_IN = System.in;
    //new
    private static final long TIME_SLEEP = 2;

    public static void main(String[] args) {
        ByteArrayInputStream enter = new ByteArrayInputStream(System.lineSeparator().getBytes());
        System.setIn(enter);
        Thread spam = new Thread(() -> Spam.main(null));
        spam.start();
        try {
            TimeUnit.SECONDS.sleep(TIME_SLEEP);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        try {
            spam.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.setIn(STD_IN);
    }
}