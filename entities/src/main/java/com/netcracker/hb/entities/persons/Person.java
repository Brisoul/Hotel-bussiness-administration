package com.netcracker.hb.entities.persons;


import lombok.Data;

@Data
public abstract class Person {

  private String name;
  private String surname;
  private int age;
  private String sex;
  private AddressInfo address;
  private PersonalCard card;

}
