package com.netcracker.hb.entities.hotel;


import com.netcracker.hb.entities.Role;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class Room implements Serializable {

  private static final long serialVersionUID = 3L;
  private UUID uuid;

  private int roomNum;
  private Role role;
  private UUID floorId;

  private Set<UUID> employeeID;
  private Set<UUID> guestID;

  public void deleteEmployee(UUID employeeN) {
    employeeID.remove(employeeN);
  }

  public void deleteGuest(UUID guestN) {
    guestID.remove(guestN);
  }


}