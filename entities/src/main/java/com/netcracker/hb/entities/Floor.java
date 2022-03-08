package com.netcracker.hb.entities;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Floor {
    private int floorNum;
    private Set Rooms = new HashSet();

    public Floor(int num){
        floorNum = num;

    }

    public Set getRoom() {
        return Rooms;
    }

    public void setRoom(Room roomN) {
        Rooms.add(roomN);
    }

    public void deleteRooms(Room roomN) {
        Rooms.remove(roomN);
    }

}
