package com.netcracker.hb.entities.persons;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class Employee extends Person implements Serializable {

  private static final long serialVersionUID = 5L;

  private Set<UUID> roomsID;
  private String username;
  private String password;
  private UUID contractID;


  public void deleteRoomsID(UUID uuid) {
    roomsID.remove(uuid);
  }



}
