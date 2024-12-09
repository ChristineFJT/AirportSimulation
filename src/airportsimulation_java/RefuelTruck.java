/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportsimulation_java;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author jingt
 */
public class RefuelTruck extends Thread {
    private LinkedBlockingQueue<Plane> refuelQueue;
    private Lock refuelLock;
    
    public RefuelTruck() {
        this.refuelQueue = new LinkedBlockingQueue<>();
        this.refuelLock = new ReentrantLock();
    }
    
    public void addToRefuelQueue(Plane plane) {
        refuelQueue.add(plane);
    }
    
    public void run() {
        int planesServed = 0;
        while(planesServed < 6) {
            if(!refuelQueue.isEmpty()) {
                Plane plane = refuelQueue.poll();
                if(plane != null) {
                    refuel(plane);
                    planesServed++;
                }
            }
        }
    }
    
    public void refuel(Plane plane) {
        refuelLock.lock();
        try {
            // Refueling process
            System.out.println("REFUELING TRUCK IS REFUELING PLANE " + plane.getPlaneID());
            int refuelPercentage = 20;
            while(refuelPercentage < 100) {
                try {
                    refuelPercentage += 20;
                    
                    // Simulate the refueling progress
                    System.out.println("PLANE " + plane.getPlaneID() + " - REFUELED " + refuelPercentage + "%");
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("PLANE " + plane.getPlaneID() + " - DONE REFUELLING");
            plane.setRefueled(true);
            synchronized (plane) {
                plane.notify();
            }
        } finally {
            refuelLock.unlock();
        }
    }
}
