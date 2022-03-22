package com.netcracker.hb.entities.persons;

import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Room;
import java.util.HashMap;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Log4j
@Builder
public class Employee extends Person implements Serializable {

  private static final long serialVersionUID = 5L;

  private Role role;
  private Set<UUID> roomsID;


  public void deleteRoomsID(UUID uuid) {
    roomsID.remove(uuid);
  }



}
