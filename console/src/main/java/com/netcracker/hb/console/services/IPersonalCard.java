package com.netcracker.hb.console.services;

import com.netcracker.hb.entities.persons.Person;

public interface IPersonalCard<T> extends Service<T>{

   void addObjectPerson(Person person);


}
