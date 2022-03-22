package com.netcracker.hb.entities.persons;

import java.util.Date;
import lombok.Data;

@Data
public class Contract {

  private Date beginContract;
  private Date endContract;
  private int salary;
  private int experience;
  private int workTime;


}
