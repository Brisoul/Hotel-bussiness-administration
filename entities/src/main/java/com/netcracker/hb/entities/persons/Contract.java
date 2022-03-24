package com.netcracker.hb.entities.persons;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class Contract {

  private static final long serialVersionUID = 8L;

  private UUID uuid;
  private Date beginContract;
  private Date endContract;
  private int salary;
  private int experience;
  private int workTime;



}
