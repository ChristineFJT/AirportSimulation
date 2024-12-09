/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportsimulation_java;

/**
 *
 * @author jingt
 */
public class RefillCrew extends Thread {
    private Plane plane;
    private String crewName;
    
    public RefillCrew(Plane plane, String crewName) {
        this.plane = plane;
        this.crewName = "PLANE " + plane.getPlaneID() + " PLANE CREW";
        System.out.println("PLANE " + plane.getPlaneID() + " - START REFILLING SUPPLIES");
    }
    
    public void run() {
        try {
            System.out.println(crewName + " - REFILLING SUPPLIES");
            
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("PLANE " + plane.getPlaneID() + " - DONE REFILLING SUPPLIES");
    }
}
