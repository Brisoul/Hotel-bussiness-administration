package com.netcracker.hb.dao.crud.person;

import com.netcracker.hb.entities.Role;

public interface PersonCRUD<T> {

  T searchObjectNameSurname(String name, String surname);

  boolean searchObjectRole(Role role);



}
