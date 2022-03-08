package com.netcracker.hb.entities;


import lombok.Data;

@Data
public class Room {
    private int roomNum;
    private Role role;

    public Room(int num){
        roomNum = num;
    }
}
