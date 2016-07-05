package com.epam.main;

import javax.naming.InsufficientResourcesException;

/**
 * Created by Igor_Filler on 7/5/2016.
 */
public class Operations {
    public static void main(String[] args) throws InsufficientFundsException {
        final Account a = new Account(1000);
        final Account b = new Account(2000);

        new Thread(new Runnable() {
            @Override
            public void run()  {
                try {
                    transfer(a, b, 500);
                } catch (InsufficientFundsException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        transfer(b,a,300);
    }
    static void transfer(Account a1, Account a2, int amount) throws InsufficientFundsException{
        if (a1.getBalance()< amount){
            throw new InsufficientFundsException("Balance trouble");
        }
        a1.withdrow(amount);
        a2.deposit(amount);
    }

    private static class InsufficientFundsException extends Throwable {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
}
