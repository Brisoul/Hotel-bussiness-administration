package com.netcracker.hb.entities;

import lombok.Data;

@Data
public class AddressInfo {

    private String countryName;
    private String townName;
    private String streetName;
    private int houseNum;
    private int entranceNum;
    private int floorNum;
    private int floatNum;

}
