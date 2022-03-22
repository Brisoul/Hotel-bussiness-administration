package com.netcracker.hb.entities.hotel;


import com.netcracker.hb.entities.Role;
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


}