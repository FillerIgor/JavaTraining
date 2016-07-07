package com.epam.main;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Igor_Filler on 7/5/2016.
 */
public class Account {
    private Lock lock;

    private int balance;

    //private int failCounter;
    private AtomicInteger failCounter = new AtomicInteger();

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Account() {
    }

    public Account(int balance) {
        lock = new ReentrantLock();
        this.balance = balance;
    }

    public void incFailCounter() {
        //failCounter++;
        //failCounter.addAndGet(1);
        failCounter.incrementAndGet();
    }

    public void withdrow(int amount) {
        balance -= amount;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public AtomicInteger getFailCounter() {
        return failCounter;
    }

    public void setFailCounter(AtomicInteger failCounter) {
        this.failCounter = failCounter;
    }
}
