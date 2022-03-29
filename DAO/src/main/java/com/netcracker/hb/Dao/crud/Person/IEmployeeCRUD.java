package com.netcracker.hb.Dao.crud.Person;

import com.netcracker.hb.Dao.crud.CRUD;
import com.netcracker.hb.entities.persons.Employee;

public interface IEmployeeCRUD<T> extends CRUD<T>, PersonCRUD<T> {

  Employee searchObjectLogIn(String username, String password);

}
