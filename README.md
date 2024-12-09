# Airport Simulation in Java

## Project Overview
This project is a simulation of an airport's air traffic control (ATC) system, designed using Java concurrent programming concepts. The simulation models the operations of an airport with multiple planes interacting concurrently, highlighting synchronization and communication between various processes.

### Objectives
- Implement synchronization and communication between concurrent processes.
- Solve synchronization challenges, such as resource sharing and prioritization.
- Collect and display statistical data regarding airport operations.

### Features
#### Basic Requirements
1. **Single Runway:** 
   - Only one runway is available for landing and departure.
   - A plane must obtain permission to land or depart.
2. **Limited Gates:** 
   - The airport has three gates for planes to dock.
   - Maximum of three planes, including those on the runway, can be present at the airport.
3. **Flight Operations:** 
   - Planes perform the following actions sequentially:
     - Land
     - Dock at a gate
     - Disembark passengers
     - Refill supplies and fuel
     - Embark new passengers
     - Depart
4. **Timing:** 
   - Each action takes a defined amount of time.

#### Additional Requirements
1. **Concurrent Activities:**
   - Passenger disembarking, embarking, and aircraft cleaning/refilling occur concurrently at different gates.
2. **Exclusive Refueling:**
   - Only one refueling truck is available, requiring exclusive access.
3. **Emergency Landing:**
   - Simulate emergency scenarios where a plane with fuel shortages is prioritized for landing.
4. **Congestion Management:**
   - Simulate scenarios with multiple planes waiting to land.

#### Statistics
- Maximum, average, and minimum waiting times for planes.
- Total planes served.
- Total passengers boarded.

## Implementation
The simulation is implemented using Java's concurrent programming facilities, including:
- **Threads**: For modeling planes, ATC, and airport operations.
- **Locks and Semaphores**: To manage resource access and synchronization.
- **Blocking Queues**: For managing landing and departure queues.

### Key Components
1. **ATC Class**: Manages landing and departure queues, grants permissions, and coordinates runway and gate usage.
2. **Plane Class**: Simulates plane behavior, including landing, docking, and refueling.
3. **Statistics Class**: Collects and reports data on airport operations.
4. **Refueling Truck**: Manages exclusive access for refueling operations.

### Sample Output
The simulation provides dynamic textual output to display:
- Major events such as landing, docking, and departures.
- Statistics summary at the end of the simulation.

## Assumptions
- A new plane arrives every 0 to 3 seconds (randomized).
- Each plane can carry a maximum of 50 passengers.
- Passengers are always ready for embarkation and disembarkation.

## How to Run
1. Compile the Java files using `javac`.
2. Run the main simulation file `AirportSimulation_Java.java' using `java`.
3. Observe the textual output detailing the simulation process.

## License
This project is open-source and available under the [MIT License](https://opensource.org/licenses/MIT).
