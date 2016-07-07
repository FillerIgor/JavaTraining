package com.epam.main;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Igor_Filler on 7/7/2016.
 */
public class Operations2 {

    public void transfer() throws InterruptedException {
        Random rand = new Random(1000);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 12; i++) {
            executorService.submit(new Transfer(new Account(rand.nextInt(5000)), new Account(rand.nextInt(5000)), rand.nextInt(10000) / 10,i));
        }
        executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
        executorService.shutdown();
    }

    public static void main(String[] args) {
        Operations2 operations2 = new Operations2();
        try {
            operations2.transfer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
