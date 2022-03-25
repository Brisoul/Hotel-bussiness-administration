package com.netcracker.hb.console.services;

import com.netcracker.hb.entities.persons.Person;

public interface IPersonalCard<T> extends Service<T>{

  public void addObjectPerson(Person person);


}
