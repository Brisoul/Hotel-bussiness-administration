package com.netcracker.hb.entities.persons;


import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Room;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@Log4j
@Builder
public class Guest extends Person implements Serializable {

  private static final long serialVersionUID = 6L;
  private UUID uuid;

  private final Role role = Role.GUEST;
  private UUID roomID;
  private Set<Service> service;


  public void deleteService(Service serviceN) {
    service.remove(serviceN);
  }


}
