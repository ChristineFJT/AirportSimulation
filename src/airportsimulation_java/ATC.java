/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportsimulation_java;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author jingt
 */
public class ATC extends Thread {
    private LinkedBlockingDeque<Plane> landingQueue;
    private LinkedBlockingDeque<Plane> departureQueue;
    private Lock runwayLock;
    Gate gate;
    Statistics stat;
    private int landedPlane = 0;
    private int departPlane = 0;
    
    public ATC(Statistics stat) {
        this.landingQueue = new LinkedBlockingDeque<>();
        this.departureQueue = new LinkedBlockingDeque<>();
        this.runwayLock = new ReentrantLock();
        this.gate = new Gate();
        this.stat = stat;
    }
    
    public void addToLandingQueue(Plane plane) {
        if(plane.isEmergency()) {
            System.out.println("ATC : PLANE " + plane.getPlaneID() + " - REQUEST FOR EMERGENCY LANDING!!");
            landingQueue.addFirst(plane);
        } else {
            System.out.println("ATC : PLANE " + plane.getPlaneID() + " - REQUEST LANDING PERMISSION");
            landingQueue.add(plane);
        }
        System.out.println("ATC - ADD PLANE " + plane.getPlaneID() + " TO LANDING QUEUE");
        synchronized(landingQueue) {
            landingQueue.notify();
        }
    }
    
    public void addToDepatureQueue(Plane plane) {
        System.out.println("ATC - ADD PLANE " + plane.getPlaneID() + " TO DEPARTURE QUEUE");
        departureQueue.add(plane);
        synchronized(departureQueue) {
            departureQueue.notify();
        }
    }
    
    
    @Override
    public void run() {
        while(departPlane <6) {
            if(!departureQueue.isEmpty()) {
                Plane plane = departureQueue.poll();
                if(runwayLock.tryLock()) {
                    plane.depart();
                    System.out.println("ATC : PLANE " + plane.getPlaneID() + " - DEPART SUCCESSFULLY.");
                } else {
                    runwayLock.unlock();
                }
                gate.gateSemaphore.release();

                gate.setAvailableGate(plane.getGateID() -1);
                departPlane++;
                stat.incrementPlanesServed();
            } else if(landedPlane < 6) {
                if(landingQueue.isEmpty()) {
                    try {
                        synchronized(landingQueue) {
                            landingQueue.wait();
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                
                if(gate.gateSemaphore.tryAcquire()) {
                    if(gate.gateSemaphore.availablePermits() >= 0) {
                        if(runwayLock.tryLock()) {
                            Plane plane = landingQueue.poll();
                            try {
                                System.out.println("ATC : PLANE " + plane.getPlaneID() + " - CAN USE RUNWAY");

                                int gateID = gate.availableGate();
                                plane.setGateID(gateID);
                                System.out.println("ATC : PLANE " + plane.getPlaneID() + " - PLEASE DOCK AT GATE " + gateID);

                                System.out.println("ATC : PLANE " + plane.getPlaneID() + " - USING RUNWAY");
                                Thread.sleep(1000);
                                System.out.println("ATC : PLANE " + plane.getPlaneID() + " - LEAVING RUNWAY");
                                Thread.sleep(100);
                                System.out.println("ATC : PLANE " + plane.getPlaneID() + " - DOCK SUCCESSFULLY AT GATE " + plane.getGateID());

                                plane.setHasGate(true);
                                plane.land();

                                landedPlane++;
                                
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            runwayLock.unlock();
                        }
                    } else {
                        gate.gateSemaphore.release();
                    }
                }
            }
            if(!departureQueue.isEmpty()) {
                synchronized(departureQueue) {
                    departureQueue.notify();
                }
            }
            if(!landingQueue.isEmpty()) {
                synchronized(landingQueue) {
                    landingQueue.notify();
                }
            }
        }
        
        stat.printStatistics();
        
    }
}
