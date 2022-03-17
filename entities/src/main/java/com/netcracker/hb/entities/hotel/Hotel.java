package com.netcracker.hb.entities.hotel;


import com.netcracker.hb.entities.persons.AddressInfo;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import lombok.Singular;

@Data
@Builder()
public class Hotel implements Serializable {

  private static final long serialVersionUID = 2L;
  private UUID uuid = UUID.randomUUID();

  private AddressInfo address;
  private Set<Floor> floors = new HashSet<>();


  public void deleteFloor(Floor floorN) {
    floors.remove(floorN);
  }

}
