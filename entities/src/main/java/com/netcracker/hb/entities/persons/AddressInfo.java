package com.netcracker.hb.entities.persons;

import lombok.Data;

import java.util.UUID;

@Data
public class AddressInfo {

    UUID uuid = UUID.randomUUID();

    private String countryName;
    private String townName;
    private String streetName;
    private int houseNum;
    private int entranceNum;
    private int floorNum;
    private int floatNum;

}
