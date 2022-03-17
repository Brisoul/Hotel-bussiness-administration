package com.netcracker.hb.Dao.CRUD;

import java.io.IOException;

public interface CRUD<T> {

  public void addObject() throws IOException, ClassNotFoundException;

  public void displayObject();

  public T searchObject(int num);

  public void deleteObject();

  public void changeObject();

  //public void saveObject(T object);

}
