/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package airportsimulation_java;

import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author jingt
 */
public class AirportSimulation_Java {
    ATC atc;
    Gate gate;
    RefuelTruck refuelTruck;
    Statistics stat;
    LinkedBlockingDeque<Plane> landingQueue;
    LinkedBlockingDeque<Plane> departureQueue;

    public AirportSimulation_Java(ATC atc, Gate gate, Statistics stat) {
        this.atc = atc;
        this.gate = gate;
        this.stat = stat;
    }
    
    public static void main(String[] args) {
        LinkedBlockingDeque<Plane> landingQueue = new LinkedBlockingDeque<>();
        LinkedBlockingDeque<Plane> departureQueue = new LinkedBlockingDeque<>();
        Statistics stat = new Statistics();
        ATC atc = new ATC(stat);
        Gate gate = new Gate();
        atc.start();
        RefuelTruck refuelTruck = new RefuelTruck();
        refuelTruck.start();
        PlaneGenerator planeGen = new PlaneGenerator(atc, gate, refuelTruck, landingQueue, departureQueue,stat);
        planeGen.start();
    }
    
}
