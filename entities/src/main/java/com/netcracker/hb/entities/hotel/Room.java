package com.netcracker.hb.entities.hotel;


import com.netcracker.hb.entities.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class Room implements Serializable {

  private static final long serialVersionUID = 123;
  private UUID uuid = UUID.randomUUID();

  private int roomNum;
  private Role role;

  private Floor floorId;


  public Room(int num) {
    roomNum = num;
  }
}
