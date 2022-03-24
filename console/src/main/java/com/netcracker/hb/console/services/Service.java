package com.netcracker.hb.console.services;

public interface Service<T> {

  //каждый метод должен сохранять добавленный объект
  public void addObject();

  public void changeObject(T object);

  public void displayObject(T object);


}
