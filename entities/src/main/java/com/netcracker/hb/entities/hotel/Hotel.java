package com.netcracker.hb.entities.hotel;


import com.netcracker.hb.entities.Department;
import com.netcracker.hb.entities.persons.AddressInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

@Data
public class Hotel implements Serializable {

  private static final long serialVersionUID = 123;
  private UUID uuid = UUID.randomUUID();

  private AddressInfo address;
  private Set<Floor> floors = new HashSet<>();


  public void deleteFloor(Floor floorN) {
    floors.remove(floorN);
  }

}
