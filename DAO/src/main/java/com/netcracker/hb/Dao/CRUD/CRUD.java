package com.netcracker.hb.Dao.CRUD;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CRUD<T> {

  public List<T> searchObjects();

  public T searchObjectNum(int num);

  public T searchUUIDObject(UUID uuid);

  public String searchFileName(T object);

  public void deleteObject(T object);

  public void saveObject(T object);

}
