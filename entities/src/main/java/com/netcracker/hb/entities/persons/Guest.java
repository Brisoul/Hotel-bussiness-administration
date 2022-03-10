package com.netcracker.hb.entities.persons;


import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.Service;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Person;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@Slf4j
public class Guest extends Person {
    private UUID uuid = UUID.randomUUID();

    final private Role role = Role.GUEST;
    private Room room;
    private Set<Service> service = new HashSet<>();

    public Guest(Room room){
        if (room.getRole() == Role.GUEST ){
            this.room = room;
        }
        else{
            log.info("Error, trying to add guest to the nonguest room");
            //Тут пока 2 нюанса как сделать так чтобы к 1 комнате нельзя было присобачить 2 гостя
            //И что делать если попытаться присобачить гостя в негостевую комнату
        }

    }

    public void setRoom(Room room) {
        if (room.getRole() == Role.GUEST ){
            this.room = room;
            log.info("Success in changing room");
        }
        else{
            log.info("Error, trying to add guest to the nonguest room");
            //Тут пока 2 нюанса как сделать так чтобы к 1 комнате нельзя было присобачить 2 гостя
            //И что делать если попытаться присобачить гостя в негостевую комнату
        }
    }


    public void deleteService(Service serviceN) {
        service.remove(serviceN);
    }






}
