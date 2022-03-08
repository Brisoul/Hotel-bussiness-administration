package com.netcracker.hb.entities;


import java.util.Set;
import java.util.HashSet;

public class Hotel {
    private AddressInfo address;
    private Set Floors = new HashSet();


    public Set getFloor() {
        return Floors;
    }

    public void setFloor(Floor floorN) {
        Floors.add(floorN);
    }

    public void deleteFloor(Floor floorN) {
        Floors.remove(floorN);
    }
}
