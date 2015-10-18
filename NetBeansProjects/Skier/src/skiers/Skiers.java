/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skiers;


 import java.util.concurrent.locks.*;


class Skier extends Thread {
    int id;
    CableCar car;
    
    public Skier(int i, CableCar car) {
        this.id = i;
        this.car = car;
    }
    
    
    
    @Override
    public void run() {
        car.getOn(id);
        
        car.getOff(id);
    }
    
}

public class Skiers {
    int skierCount;
    
    public Skiers(int count, int capacity) {
        this.skierCount = count;
        CableCar car = new CableCar(capacity);
        car.start();
        
        for (int i = 0; i < count; i++) {
            Skier skier = new Skier(i, car);
            skier.start();
        }
    }
    
    public static void main(String[] args) {
        new Skiers(9,3);
    }

     
    
}
