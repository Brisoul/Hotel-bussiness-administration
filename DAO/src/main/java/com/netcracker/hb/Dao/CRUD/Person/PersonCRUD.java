package com.netcracker.hb.Dao.CRUD.Person;

import com.netcracker.hb.entities.Role;

public interface PersonCRUD<T> {

  public T searchObjectNameSurname(String name, String surname);

  public boolean searchObjectRole(Role role);



}
