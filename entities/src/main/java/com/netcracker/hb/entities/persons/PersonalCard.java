package com.netcracker.hb.entities.persons;

import com.netcracker.hb.entities.Role;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class PersonalCard implements Serializable {

  private static final long serialVersionUID = 7L;
  private UUID uuid;

  private int num;
  private Date expireDate;
  private Role role;
  private UUID personID;
}
