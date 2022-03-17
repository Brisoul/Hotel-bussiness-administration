package com.netcracker.hb.entities.hotel;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class Floor implements Serializable {

  private static final long serialVersionUID = 1L;
  private UUID uuid = UUID.randomUUID();

  private int floorNum;
  private Set<Room> rooms = new HashSet<>();
  private Hotel hotelId;

  public Floor() {
  }

  public void deleteRooms(Room roomN) {
    rooms.remove(roomN);
  }

}


