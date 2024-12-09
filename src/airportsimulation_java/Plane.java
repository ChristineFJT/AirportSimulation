/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportsimulation_java;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author jingt
 */
public class Plane extends Thread {
    private int planeID;
    private int gateID;
    ATC atc;
    RefuelTruck refuelTruck;
    public Gate gate;
    LinkedBlockingDeque<Plane> landingQueue;
    LinkedBlockingDeque<Plane> departureQueue;
    private Random rand;
    private int numPassenger;
    private boolean isLanded;
    private boolean hasGate;
    private boolean isDisembarked;
    private boolean isRefueled;
    private boolean isCleanedAndRefilled;
    private boolean isEmbarked;
    private boolean isTakenOff;
    private boolean Emergency;
    
    private long arrivalTime;
    private long departureTime;
    private Statistics stat;
    
    public Plane(int planeID, ATC atc, Gate gate, RefuelTruck refuelTruck, LinkedBlockingDeque landingQueue, LinkedBlockingDeque departureQueue, Statistics stat) {
        this.planeID = planeID;
        this.atc = atc;
        this.gate = gate;
        this.refuelTruck = refuelTruck;
        this.landingQueue = landingQueue;
        this.departureQueue = departureQueue;
        this.rand = new Random();
        this.numPassenger = numPassenger;
        this.isLanded = false;
        this.hasGate = false;
        this.isDisembarked = false;
        this.isRefueled = false;
        this.isCleanedAndRefilled = false;
        this.isEmbarked = false;
        this.isTakenOff = false;
        this.Emergency = false;
        this.arrivalTime = 0;
        this.departureTime = 0;
        this.stat = stat;
    }

    public int getPlaneID() {
        return planeID;
    }
    
    public int getGateID() {
        return gateID;
    }
    
    public void setGateID(int gateID) {
        this.gateID = gateID;
    }

    
    public int getNumPassenger() {
        return numPassenger;
    }

    public void setNumPassenger(int numPassenger) {
        this.numPassenger = numPassenger;
    }

    public boolean isHasGate() {
        return hasGate;
    }
    
    public void setHasGate(boolean hasGate) {
        this.hasGate = hasGate;
    }
    
    public void setRefueled(boolean isRefueled) {
        this.isRefueled = isRefueled;
    }

    public void setEmergency(boolean Emergency) {
        this.Emergency = Emergency;
    }
    
    public boolean isEmergency() {
        return Emergency;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    
    public long getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(long departureTime) {
        this.departureTime = departureTime;
    }
    
    public long getWaitingTime() {
        if(departureTime == 0) {
            return 0;
        }
        return departureTime - arrivalTime;
    }

    public void run() {
        try {
            requestLandingPermission();
            land();
            disembarkPassengers();
            cleaningAndRefill();
            refuelingTruck();
            embarkPassengers();
            requestDepartPermission();
            depart();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void requestLandingPermission() {
        atc.addToLandingQueue(this);
        setArrivalTime(System.currentTimeMillis()); // Set arrival time
    }
    
    public void land() {
        synchronized(this) {
            while(!isHasGate()) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            isLanded = true;
            notify();
        }
    }
    
    public void disembarkPassengers() throws InterruptedException {
        while(!isLanded) {
            wait();
        }
        int passengers = rand.nextInt(50 - 30 + 1) + 30;
        setNumPassenger(passengers);
        for (int i = 1; i <= passengers; i++) {
            System.out.println("\t\tPLANE " + planeID + " : DISEMBARKING PASSENGER " + i + " [" + i + "/" + passengers + "]");
            Thread.sleep(rand.nextInt(500)); // passenger disembarking time
        }
        System.out.println("PLANE " + planeID + " - DONE DISEMBARKING " + passengers + " PASSENGERS...");
        isDisembarked = true;
    }

    public void cleaningAndRefill() {
        try {
            while(!isDisembarked) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            PlaneCrew cleaningCrew = new PlaneCrew(this,"Cleaning Crew");
            cleaningCrew.start();
            RefillCrew refillCrew = new RefillCrew(this,"Refilling Crew");
            refillCrew.start();
            cleaningCrew.join();
            refillCrew.join();
            isCleanedAndRefilled = true;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void refuelingTruck() throws InterruptedException {
        while(!isLanded) {
            wait();
        }
        refuelTruck.addToRefuelQueue(this);
        isRefueled = true;
    }

    public void embarkPassengers() throws InterruptedException {
        while(!isCleanedAndRefilled) {
            wait();
        }
        int passengers = getNumPassenger();
        for (int i = 1; i <= passengers; i++) {
            System.out.println("\t\tPLANE " + planeID + " : EMBARKING PASSENGER " + i + " [" + i + "/" + passengers + "]");
            Thread.sleep(rand.nextInt(500)); // passenger disembarking time
        }
        System.out.println("PLANE " + planeID + " - DONE EMBARKING " + passengers + " PASSENGERS...");
        isEmbarked = true;
        stat.addPassengerBoarded(passengers);
    }

    public void requestDepartPermission() throws InterruptedException {
        while(!isRefueled || !isEmbarked || !isCleanedAndRefilled) {
            wait();
        }
        System.out.println("ATC : PLANE " + planeID + " - REQUEST DEPART PERMISSION");
        atc.addToDepatureQueue(this);
        setDepartureTime(System.currentTimeMillis()); // Set departure time
        stat.updateWaitingTime(this);
    }

    public void depart() {
        isTakenOff = true;
        synchronized(landingQueue) {
            landingQueue.notify();
        }
    }
}
