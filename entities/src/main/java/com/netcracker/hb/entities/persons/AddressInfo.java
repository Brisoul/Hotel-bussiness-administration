package com.netcracker.hb.entities.persons;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AddressInfo implements Serializable {

  private static final long serialVersionUID = 123;
  private UUID uuid = UUID.randomUUID();

  private String countryName;
  private String townName;
  private String streetName;
  private int houseNum;
  private int entranceNum;
  private int floorNum;
  private int floatNum;

}
