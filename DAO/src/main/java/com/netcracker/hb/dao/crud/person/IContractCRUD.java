package com.netcracker.hb.dao.crud.person;

import com.netcracker.hb.dao.crud.CRUD;
import java.util.List;
import java.util.UUID;

public interface IContractCRUD<T> {

  List<T> searchObjects();

  T searchUUIDObject(UUID uuid);

  String searchFileName(T object);

  void deleteObject(T object);

  void saveObject(T object);

}
