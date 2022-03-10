package com.netcracker.hb.entities.hotel;


import com.netcracker.hb.entities.persons.AddressInfo;
import com.netcracker.hb.entities.Department;
import lombok.Data;

import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

@Data
public class Hotel {
    private UUID uuid = UUID.randomUUID();
    private AddressInfo address;
    private Set<Floor> floors = new HashSet<>();
    private Set<Department> departments = new HashSet<>();

    public void deleteFloor(Floor floorN) {
        floors.remove(floorN);
    }

    public void deleteDepartments(Department DepartmentN) {
        departments.remove(DepartmentN);
    }
}
