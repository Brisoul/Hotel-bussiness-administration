package com.netcracker.hb.console.services;

public interface Service<T> {

  //каждый метод должен сохранять добавленный объект
  void addObject();

  void changeObject(T object);

  void displayObject(T object);


}
