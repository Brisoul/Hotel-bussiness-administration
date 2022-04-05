package com.netcracker.hb.entities.persons;


import com.netcracker.hb.entities.Role;
import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public abstract class Person implements Serializable {

  private static final long serialVersionUID = 9L;

  private UUID uuid;

  private String name;
  private String surname;
  private int age;
  private String sex;
  private Role role;
  private UUID cardID;



}
