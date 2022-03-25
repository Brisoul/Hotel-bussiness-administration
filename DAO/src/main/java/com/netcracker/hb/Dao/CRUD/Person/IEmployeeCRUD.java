package com.netcracker.hb.Dao.CRUD.Person;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.entities.persons.Employee;

public interface IEmployeeCRUD<T> extends CRUD<T>, PersonCRUD<T> {

  public Employee searchObjectLogIn(String username, String password);

}
