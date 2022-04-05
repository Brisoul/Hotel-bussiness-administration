package com.netcracker.hb.entities.persons;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
public class Guest extends Person implements Serializable {

  private static final long serialVersionUID = 6L;
  private UUID roomID;
  private Set<Service> service;


  public void deleteService(Service serviceN) {
    service.remove(serviceN);
  }


}
