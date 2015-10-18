/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountmonitor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Jakub
 */
public class AccountMonitor {
    private final Lock lock = new ReentrantLock();
    private final Condition enough = lock.newCondition();
    private long Balance;
    
    
    public AccountMonitor(long initialBalance) {
        this.Balance = initialBalance;
    }
    
    public void deposit(int amount) {
        lock.lock();
        Balance += amount;
        enough.signal();
        lock.unlock();
    }
    public void withdraw(int amount) throws InterruptedException {
        lock.lock();
        while(Balance < amount) enough.await();
        Balance-=amount;
        lock.unlock();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
