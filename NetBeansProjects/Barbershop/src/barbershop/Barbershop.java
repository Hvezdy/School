/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barbershop;
import java.util.concurrent.Semaphore;

public class Barbershop {
private Semaphore[] chairs = new Semaphore[3];
private Semaphore[] started = new Semaphore[3];
private Semaphore[] finished = new Semaphore[3];

public Barbershop(int customers) {
    for (int i = 0; i < 3; i++) {
        chairs[i] = new Semaphore(0);
        finished[i] = new Semaphore(0);
        started[i] = new Semaphore(0);
    }
    
    for (int i = 0; i < 3; i++) {
        Barber b = new Barber(i);
        b.start();
    }
    
    for (int i = 0; i < customers; i++) {
        Customer c = new Customer(i);
        c.start();
    }
}

public class Barber extends Thread{
    int id;
    public Barber(int id) {
        this.id = id;
    }
    
    
    @Override
    public void run(){
        try {
            
        while(true) {    
        System.out.println("Barber " + id + " is having nap");
        chairs[id].release();
        started[id].acquire();
        System.out.println("Barber " + id + " is working");
        int random = (int)(Math.random() * ( 1000 ));
        Thread.sleep(random);
        System.out.println("Barber " + id + " is finished");
        finished[id].release();
        }
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e);
        }
        
       
    }
}

public class Customer extends Thread{
    int id;
    
    public Customer(int id) {
        this.id = id;   
    }
    
    @Override
    public void run() {
        try {
        System.out.println("Customer " + id + " waiting for barber " + id%3);
        chairs[id%3].acquire();
        System.out.println("Customer " + id + " got on turn " + id%3);
        started[id%3].release();
        finished[id%3].acquire();
        System.out.println("Customer " + id + " left barber " + id%3);
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e);
        }
    }
}

    
    public static void main(String[] args) {
        new Barbershop(9);
    }
    
}
