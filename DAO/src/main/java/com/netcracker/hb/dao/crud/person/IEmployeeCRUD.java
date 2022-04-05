package com.netcracker.hb.dao.crud.person;

import com.netcracker.hb.dao.crud.CRUD;
import com.netcracker.hb.entities.persons.Employee;

public interface IEmployeeCRUD<T> extends CRUD<T>, PersonCRUD<T> {

  Employee searchObjectLogIn(String username, String password);

}
