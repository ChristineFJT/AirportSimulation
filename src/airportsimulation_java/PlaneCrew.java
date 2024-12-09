/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportsimulation_java;

/**
 *
 * @author jingt
 */
public class PlaneCrew extends Thread {
    private Plane plane;
    private String crewName;
    
    public PlaneCrew(Plane plane, String crewName) {
        this.plane = plane;
        this.crewName = "PLANE " + plane.getPlaneID() + " PLANE CREW";
        System.out.println("PLANE " + plane.getPlaneID() + " - START CLEANING");
    }
    
    public void run() {
        for( int i = 1; i < 4; i++) {
            try {
                switch(i) {
                    case 1:
                        System.out.println(crewName + " - CLEANING SEATS...");
                        break;
                    case 2:
                        System.out.println(crewName + " - SWEEPING FLOOR...");
                        break;
                    case 3:
                        System.out.println(crewName + " - THROWING TRASH...");
                        break;
                }
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("PLANE " + plane.getPlaneID() + " - DONE CLEANING");
    }
}
