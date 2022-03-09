package com.netcracker.hb.entities;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;


@Data
@Slf4j
public class Guest extends Person {

    final private Role role = Role.GUEST;
    private Room room;
    private Set service = new HashSet();

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

    public Set getService() {
        return service;
    }

    public void setService(Service serviceN) {
        service.add(serviceN);
    }

    public void deleteService(Service serviceN) {
        service.remove(serviceN);
    }






}
