package com.netcracker.hb.entities.persons;

import com.netcracker.hb.entities.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class PersonalCard implements Serializable {

  private static final long serialVersionUID = 123;
  private UUID uuid = UUID.randomUUID();

  private int num;
  private Date expireDate;
  private Role role;
}
