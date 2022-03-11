package com.netcracker.hb.entities.hotel;

import com.netcracker.hb.entities.hotel.Room;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Floor implements Serializable {

  private static final long serialVersionUID = 123;
  private UUID uuid = UUID.randomUUID();

  private int floorNum;
  private Set<Room> rooms = new HashSet<>();
  private Hotel hotelId;


  public Floor(int num) {
    floorNum = num;

  }

  public void deleteRooms(Room roomN) {
    rooms.remove(roomN);
  }

}
