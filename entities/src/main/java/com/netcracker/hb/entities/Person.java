package com.netcracker.hb.entities;

import lombok.Data;

@Data
public class Person {

    private String name;
    private String surname;
    private int age;
    private String sex;
    private AddressInfo address;
    private PCard card;
    private Role role;

}
