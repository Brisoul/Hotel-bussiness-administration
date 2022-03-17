package com.netcracker.hb.entities.persons;

import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Room;
import java.util.HashMap;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Slf4j
@Builder
public class Employee extends Person implements Serializable {

  private static final long serialVersionUID = 5L;
  private UUID uuid;

  private Role role;
  private Set<Room> rooms = new HashSet<>();
  private HashMap<String, String> userCredits = new HashMap<String, String>();

  public void setRooms(Room roomsN) {
    switch (this.role) {

      case ADMIN:
        rooms.add(roomsN);
        log.info("Success adding room to ADMIN employee");
        break;

      case MANAGER:
        if (roomsN.getRole() != Role.ADMIN) {
          rooms.add(roomsN);
          log.info("Success adding room to MANAGER employee");
        } else {
          log.info("Error with adding room to MANAGER employee, und employee.role");
        }
        break;

      case SERVICE_EMPLOYEE:
        if (roomsN.getRole() != Role.ADMIN && roomsN.getRole() != Role.MANAGER) {
          rooms.add(roomsN);
          log.info("Success adding room to  SERVICE employee");
        } else {
          log.info("Error with adding room to SERVICE employee, und employee.role");
        }
        break;

      default:
        log.info("Error with adding room und employee.role");
    }

  }

  public void deleteRooms(Room roomsN) {
    rooms.remove(roomsN);
  }


  @Data
  public class Contract {

    private Date beginContract;
    private Date endContract;
    private int salary;
    private int experience;
    private int workTime;


  }
}
