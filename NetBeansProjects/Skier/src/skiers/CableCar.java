/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skiers;

import java.util.concurrent.locks.*;

/**
 *
 * @author Jakub
 */
public class CableCar extends Thread {

    private final Lock lock = new ReentrantLock();
    private final Condition isFull = lock.newCondition();
    private final Condition isEmpty = lock.newCondition();
    private final Condition isReady = lock.newCondition();
    private final Condition isUp = lock.newCondition();
    private String position = "DOWN";
    private int capacity;
    private int passangers;

    public CableCar(int capacity) {
        this.capacity = capacity;
        this.passangers = 0;
    }
    
    public void getOn(int i) {
        try {
        lock.lock();
        System.out.println("Skier " + i + " is in line");
        while(position != "Down" && passangers >= capacity) isReady.await();
        passangers++;
        System.out.println("Skier " + i + " got onto the car");
        if(passangers == capacity) isFull.signal();
        lock.unlock();
        } catch(InterruptedException e) {
            System.out.println("Exception:" + e);
        }
    }
    
    public void getOff(int i) {
        try {
            lock.lock();
            while(position != "UP") isUp.await();
            passangers--;
            System.out.println("Skier " + i + " got off car");
            if(passangers == 0) isEmpty.signal();
            lock.unlock();
        }catch(InterruptedException e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                lock.lock();
                System.out.println("Cable car is " + position);
                System.out.println("Cable car waiting for passangers");
                while (passangers != capacity) {
                    isFull.await();
                }
                System.out.println("Cable car going UP");
                Thread.sleep(1000);
                position = "UP";
                isUp.signalAll();
                isEmpty.await();
                System.out.println("Cable car going DOWN");
                Thread.sleep(1000);
                position = "DOWN";
                isReady.signalAll();
                lock.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println("INTERRUPTED EXCEPTION CABLE CAR : " + e);
        }
    }

}
