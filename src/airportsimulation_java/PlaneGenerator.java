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
public class PlaneGenerator extends Thread {
    ATC atc;
    Gate gate;
    RefuelTruck refuelTruck;
    LinkedBlockingDeque<Plane> landingQueue;
    LinkedBlockingDeque<Plane> departureQueue;
    private Random rand;
    Statistics stat;
    
    public PlaneGenerator(ATC atc, Gate gate, RefuelTruck refuelTruck,LinkedBlockingDeque landingQueue, LinkedBlockingDeque departureQueue, Statistics stat) {
        this.atc = atc;
        this.gate = gate;
        this.refuelTruck = refuelTruck;
        this.rand = new Random();
        this.landingQueue = landingQueue;
        this.departureQueue = departureQueue;
        this.stat = stat;
    }
    
    @Override
    public void run() {
        for(int i = 1; i <= 6; i++) {
            try {
                Thread.sleep(rand.nextInt(4)*1000);
                Plane plane = new Plane(i,atc,gate, refuelTruck, landingQueue, departureQueue,stat);
                if(i == 6) {
                    plane.setEmergency(true);
                }
                plane.start();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
