package com.netcracker.hb.Dao.crud.Person;

import com.netcracker.hb.Dao.crud.CRUD;

public interface IContractCRUD<T> extends CRUD<T> {
  T searchObjectNameSurname(String name, String surname);

}
