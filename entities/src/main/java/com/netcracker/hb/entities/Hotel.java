package com.netcracker.hb.entities;


import lombok.Data;

import java.util.Set;
import java.util.HashSet;

@Data
public class Hotel {
    private AddressInfo address;
    private Set Floors = new HashSet();
    private Set Departments = new HashSet();


    public Set getFloor() {
        return Floors;
    }

    public void setFloor(Floor floorN) {
        Floors.add(floorN);
    }

    public void deleteFloor(Floor floorN) {
        Floors.remove(floorN);
    }


    public Set getDepartments() {
        return Departments;
    }

    public void setDepartments(Department DepartmentN) {
        Departments.add(DepartmentN);
    }

    public void deleteDepartments(Department DepartmentN) {
        Departments.remove(DepartmentN);
    }
}
