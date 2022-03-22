package com.netcracker.hb.Dao.CRUD;

import java.io.IOException;
import java.util.UUID;

public interface CRUD<T> {

  public T searchObject(int num);

  public T searchUUIDObject(UUID uuid);

  public String searchFileName(T object);

  public void deleteObject(T object);

  public void saveObject(T object);

}
