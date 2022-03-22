package com.netcracker.hb.Dao.CRUD.Person;


import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.entities.persons.Employee;
import java.util.UUID;

public class EmployeeCRUD implements CRUD<Employee> {


  @Override
  public Employee searchObject(int num) {
    return null;
  }

  @Override
  public Employee searchUUIDObject(UUID uuid) {
    return null;
  }

  @Override
  public String searchFileName(Employee object) {
    return null;
  }

  @Override
  public void deleteObject(Employee object) {

  }

  @Override
  public void saveObject(Employee object) {

  }
}
