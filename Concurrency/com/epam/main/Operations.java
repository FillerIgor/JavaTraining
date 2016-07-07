package com.epam.main;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import javax.naming.InsufficientResourcesException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Igor_Filler on 7/5/2016.
 */
public class Operations {
    final static Logger logger = Logger.getLogger(Operations.class);
    public static void main(String[] args) throws InsufficientFundsException, InterruptedException {
        final Account a = new Account(1000);
        final Account b = new Account(2000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    transfer(a, b, 500);
                } catch (InsufficientFundsException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    logger.error("InterruptedException",e);
                    e.printStackTrace();
                }
            }
        }).start();
        transfer(b, a, 300);
    }

    static void transfer(Account a1, Account a2, int amount) throws InsufficientFundsException, InterruptedException {

        if (a1.getBalance() < amount) {
            throw new InsufficientFundsException("Balance trouble");
        }
        if (a1.getLock().tryLock(3, TimeUnit.MILLISECONDS)){
            try{
                logger.error("First lock!");
                if(a2.getLock().tryLock(3, TimeUnit.MILLISECONDS)){
                  try{
                      logger.error("Transfer!");
                      a1.withdrow(amount);
                      a2.deposit(amount);
                      Thread.sleep(2000);
                  }finally {
                      a2.getLock().unlock();
                  }
                }else {
                    logger.error("Sorry! a2");
                    a2.incFailCounter();
                }
            } finally {
                a1.getLock().unlock();
            }
        } else {
            logger.error("Sorry! a1");
            a1.incFailCounter();
        }
/*        synchronized (a1) {
            Thread.sleep(1000);
            logger.debug("First syncro");
           // System.out.println("First syncro");
            synchronized (a2) {
                System.out.println("Second syncro");
                a1.withdrow(amount);
                a2.deposit(amount);
            }
        }*/
        logger.error("failCounter a1:"+a1.getFailCounter()+" a2:"+ a2.getFailCounter());
    }

    private static class InsufficientFundsException extends Throwable {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
}
