package com.netcracker.hb.entities.hotel;


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
  private UUID uuid;
  private Set<UUID> floorsID ;


  public void deleteFloor(UUID floorN) {
    floorsID.remove(floorN);
  }

}
