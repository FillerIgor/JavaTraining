package com.epam.main;

/**
 * Created by Igor_Filler on 7/5/2016.
 */
public class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public void withdrow(int amount){
        balance-=amount;
    }
    public void deposit(int amount){
        balance+=amount;
    }
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
