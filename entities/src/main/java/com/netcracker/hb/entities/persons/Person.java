package com.netcracker.hb.entities.persons;


import java.util.UUID;
import lombok.Data;

@Data
public abstract class Person {

  private UUID uuid;

  private String name;
  private String surname;
  private int age;
  private String sex;

  private AddressInfo address;
  private PersonalCard card;

  private String username;
  private String password;

}
