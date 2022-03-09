package com.netcracker.hb.entities;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
public class Employee extends Person{

    private Role role;
    private Set rooms = new HashSet();


    public Employee(Role role){
        this.role = role;
    }

    public Set getRooms() {
        return rooms;
    }

    public void setRooms(Room roomsN) {
        switch(this.role) {

            case ADMIN:
                rooms.add(roomsN);
                log.info("Success adding room to ADMIN employee");
                break;

            case MANAGER:
                if (roomsN.getRole() != Role.ADMIN) {
                    rooms.add(roomsN);
                    log.info("Success adding room to MANAGER employee");
                }
                else {
                    log.info("Error with adding room to MANAGER employee, und employee.role");
                }
                break;

            case SERVICE_EMPLOYEE:
                if (roomsN.getRole() != Role.ADMIN && roomsN.getRole() != Role.MANAGER) {
                    rooms.add(roomsN);
                    log.info("Success adding room to  SERVICE employee");
                }
                else {
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
    public class Contract{
        private Date beginContract;
        private Date endContract;
        private int salary;
        private int experience;
        private int workTime;
        

    }
}
