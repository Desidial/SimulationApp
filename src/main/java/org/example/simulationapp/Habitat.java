package org.example.simulationapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class Habitat {
    public static final int TRUCK_GENERATION_INTERVAL = 3;
    public static final double TRUCK_PROBABILITY = 0.5;
    public static final int CAR_GENERATION_INTERVAL = 2;
    public static final double CAR_PROBABILITY = 0.7;

    private final List<Vehicle> vehicles = new ArrayList<>();
    private final TreeSet<String> uniqueIds = new TreeSet<>();
    private final HashMap<Long, Vehicle> vehiclesByBirthTime = new HashMap<>();
    private int truckCount = 0;
    private int carCount = 0;

    public void startSimulation() {
        vehicles.clear();
        uniqueIds.clear();
        vehiclesByBirthTime.clear();
        truckCount = 0;
        carCount = 0;
    }

    public void stopSimulation() {
        vehicles.clear();
    }

    public void update(long elapsedSeconds) {
        // Логика для обновления симуляции
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        uniqueIds.add(vehicle.getId());
        vehiclesByBirthTime.put(System.currentTimeMillis(), vehicle);
        if (vehicle instanceof Truck) {
            truckCount++;
        } else if (vehicle instanceof Car) {
            carCount++;
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public int getTruckCount() {
        return truckCount;
    }

    public int getCarCount() {
        return carCount;
    }
}
