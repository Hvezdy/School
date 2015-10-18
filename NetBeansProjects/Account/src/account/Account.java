/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;
import java.util.concurrent.*;
/**
 *
 * @author Jakub
 */
public class Account {
    private long balance;
    private Semaphore access;
    /**
     * @param args the command line arguments
     */
    public Account(long initialBalance) {
        this.balance = initialBalance;
        this.access = new Semaphore(1);
    }

    public boolean deposit(long amount) {
        try{
            access.acquire();
            balance+=amount;
            access.release();
            return true;
        }catch(InterruptedException e) {
            System.out.println("Exception:" + e);
            return false;
        }
        
    }
        
    public boolean withdraw(long amount) {
        try {
            access.acquire();
            if(balance < amount) {
                access.release();
                return withdraw(amount);
            } else {
                balance-=amount;
                access.release();
                return true;
            }
        } catch(InterruptedException e) {
            System.out.println("Exception:" + e);
            return false;
        }
    }

    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
