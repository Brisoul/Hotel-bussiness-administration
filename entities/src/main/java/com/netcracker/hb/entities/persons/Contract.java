package com.netcracker.hb.entities.persons;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contract implements Serializable {

  private static final long serialVersionUID = 8L;

  private UUID employeeID;
  private UUID uuid;
  private Date beginContract;
  private Date endContract;
  private int salary;
  private int experience;
  private int workTime;



}
