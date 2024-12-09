/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportsimulation_java;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jingt
 */
public class Statistics {
    private List<Gate> gates;
    private List<Double> waitingTimes;
    private long totalWaitingTime;
    private double maxWaitingTime;
    private double minWaitingTime;
    private int numPlanesServed;
    private int numPassengersBoarded;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");
    
    public Statistics() {
        gates = new ArrayList<>();
        waitingTimes = new ArrayList<>();
        totalWaitingTime = 0;
        maxWaitingTime = Double.MIN_VALUE;
        minWaitingTime = Double.MAX_VALUE;
        numPlanesServed = 0;
        numPassengersBoarded = 0;
    }
    
    
    public void updateWaitingTime(Plane plane) {
        double waitingTimeInSeconds = plane.getWaitingTime() / 1000.0;
        waitingTimes.add(waitingTimeInSeconds);
        totalWaitingTime += waitingTimeInSeconds;
        maxWaitingTime = Math.max(maxWaitingTime, waitingTimeInSeconds);
        minWaitingTime = Math.min(minWaitingTime, waitingTimeInSeconds);
    }
    
    public void incrementPlanesServed() {
        numPlanesServed++;
    }
    
    public void addPassengerBoarded(int passengers) {
        numPassengersBoarded += passengers;
    }
    
    public void printStatistics() {
        System.out.println("\n\n");
        System.out.println("========== AIRPORT STATISTICS ==========");

        if(areAllGatesEmpty()) {
            System.out.println("ALL GATES ARE INDEED EMPTY.");
        } else {
            System.out.println("GATES ARE NOT EMPTY YET.");
        }
        
        System.out.println("Number of planes served: " + numPlanesServed);
        System.out.println("Number of passsngers boarded: " + numPassengersBoarded);
        System.out.println("Maximum waiting time for a plane: " + DECIMAL_FORMAT.format(maxWaitingTime) + " seconds.");
        System.out.println("Average waiting time for a plane: " + DECIMAL_FORMAT.format(getAverageWaitingTime()) + " seconds.");
        System.out.println("Minimum waiting time for a plane: " + DECIMAL_FORMAT.format(minWaitingTime) + " seconds.");
    }
    
    public boolean areAllGatesEmpty() {
        for(Gate gate : gates) {
            if(!gate.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    private double getAverageWaitingTime() {
        if(numPlanesServed > 0) {
            return (double) totalWaitingTime / numPlanesServed;
        }
        return 0;
    }
}
