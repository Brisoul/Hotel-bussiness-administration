package com.netcracker.hb.entities.hotel;

import com.netcracker.hb.entities.hotel.Room;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Floor {
    UUID uuid = UUID.randomUUID();
    private int floorNum;
    private Set<Room> rooms = new HashSet<>();

    public Floor(int num){
        floorNum = num;

    }

    public void deleteRooms(Room roomN) {
        rooms.remove(roomN);
    }

}
