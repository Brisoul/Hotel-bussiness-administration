package com.netcracker.hb.Dao.crud;

import java.util.List;
import java.util.UUID;

public interface CRUD<T> {

  List<T> searchObjects();

  T searchObjectNum(int num);

  T searchUUIDObject(UUID uuid);

  String searchFileName(T object);

  void deleteObject(T object);

  void saveObject(T object);

}
