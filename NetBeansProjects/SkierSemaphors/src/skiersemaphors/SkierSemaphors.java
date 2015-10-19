/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skiersemaphors;

import java.util.concurrent.Semaphore;

public class SkierSemaphors {

    private int skierCount;
    private int capacity;
    private int rounds;

    public Semaphore waitingIn;
    public Semaphore boarding;
    public Semaphore waitingOff;
    public Semaphore leaving;

    public SkierSemaphors(int count, int cap) {
        this.skierCount = count;
        this.capacity = cap;
        this.rounds = skierCount / capacity;
        waitingIn = new Semaphore(0);
        boarding = new Semaphore(0);
        waitingOff = new Semaphore(0);
        leaving = new Semaphore(0);
        
        
        CableCar car = new CableCar(capacity,rounds);
        car.start();
        
        for (int i = 0; i < skierCount; i++) {
            Skier skier = new Skier(i);
            skier.start();
        }
        
    }

    public static void main(String[] args) {
        new SkierSemaphors(9,3);
    }

    public class CableCar extends Thread {

        private int capacity;
        private int rounds;

        public CableCar(int capacity, int rounds) {
            this.capacity = capacity;
            this.rounds = rounds;
        }

        @Override
        public void run() {
            for (int i = 0; i < rounds; i++) {
                try {
                    System.out.println("Car waiting DOWN and letting people in");
                    for (int j = 0; j < capacity; j++) {
                        waitingIn.release();
                    }
                    for (int j = 0; j < capacity; j++) {
                        boarding.acquire();
                    }
                    System.out.println("Car going up the hill");
                    Thread.sleep(1000);
                    System.out.println("Car up on hill letting people out");
                    for (int j = 0; j < capacity; j++) {
                        waitingOff.release();
                    }
                    for (int j = 0; j < capacity; j++) {
                        leaving.acquire();
                    }
                    System.out.println("Car is going DOWN");
                    Thread.sleep(1000);
                    

                } catch (InterruptedException e) {
                    System.out.println("Exception: " + e);
                }
            }
        }

    }

    class Skier extends Thread {
        public int id;
        
        public Skier(int id) {
            this.id = id;
        }
        
        @Override
        public void run() {
            try{
            System.out.println("Skier " + id + " is waiting in line");
            waitingIn.acquire();
            System.out.println("Skier " + id + " is getting into car");
            int random = (int)(Math.random() * ( 500 ));
            Thread.sleep(random);
            boarding.release();
            System.out.println("Skier "+id+" boarded car");
            waitingOff.acquire();
            System.out.println("Skier "+id+" is preparing to leave car");
            Thread.sleep(random);
            leaving.release();
            System.out.println("Skier "+id+" left car");
            
            
            } catch(InterruptedException e) {
                System.out.println("Exception: " + e);
            }
        }
    }

}
