package com.epam.main;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Igor_Filler on 7/7/2016.
 */
public class Transfer implements Callable<Boolean> {
    private Logger logger = Logger.getLogger(Transfer.class);
    private Account accountFrom;
    private Account accountTo;
    private int amount;

    private int id;

    public Transfer(Account accountFrom, Account accountTo, int amount,int id) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.id=id;
    }

    @Override
    public Boolean call() throws Exception {
        logger.error("Balance accountFrom before:" + accountFrom.getBalance() + " Balance accountTo before:" + accountTo.getBalance()+" amount:" + amount+ " id " + this.id);
        if (accountFrom.getBalance() < amount) {
            try {
                throw new InsufficientFundsException("Balance trouble"+ "Balance accountFrom before:" + accountFrom.getBalance() + " Balance accountTo before:" + accountTo.getBalance()+" amount:" + amount+ " id " + this.id);
            } catch (InsufficientFundsException e) {
                e.printStackTrace();
            }
        }
        if (accountFrom.getLock().tryLock(3, TimeUnit.MILLISECONDS)) {
            try {
                logger.error("First lock!");
                if (accountTo.getLock().tryLock(3, TimeUnit.MILLISECONDS)) {
                    try {
                        Random rand = new Random(1000);
                        logger.error("Transfer!");
                        accountFrom.withdrow(amount);
                        accountTo.deposit(amount);
                        Thread.sleep(rand.nextInt(5000));
                    } finally {
                        accountTo.getLock().unlock();
                    }
                } else {
                    logger.error("Sorry! a2");
                    accountTo.incFailCounter();
                }
            } finally {
                accountFrom.getLock().unlock();
            }
        } else {
            logger.error("Sorry! a1");
            accountFrom.incFailCounter();
            return false;
        }
        //logger.error("failCounter a1:" + accountFrom.getFailCounter() + " accountTo:" + accountTo.getFailCounter());
        logger.error("Balance accountFrom:" + accountFrom.getBalance() + " Balance accountTo:" + accountTo.getBalance()+ " id " + this.id);

        return true;
    }

    private static class InsufficientFundsException extends Throwable {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
