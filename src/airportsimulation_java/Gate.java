/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportsimulation_java;

import java.util.concurrent.Semaphore;

/**
 *
 * @author jingt
 */
public class Gate {
    private Plane plane;
    Semaphore gateSemaphore = new Semaphore(3);
    boolean[] gateStat = {true,true,true};
    
    public int availableGate() {
        for(int i = 0; i < 3; i++) {
            if(gateStat[i]==true) {
                gateStat[i] = false;
                return i+1;
            }
        }
        return -1;
    }
    
    public void setAvailableGate(int index) {
        if(index >= 0 && index < gateStat.length) {
            gateStat[index] = true;
        } else {
            System.out.println("wrong index");
        }
    }
    
    public boolean isEmpty() {
        return plane == null;
    }
}
