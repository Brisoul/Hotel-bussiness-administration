package com.netcracker.hb.entities.persons;

import com.netcracker.hb.entities.Role;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PersonalCard {
    private UUID uuid = UUID.randomUUID();
    private int num;
    private Date expireDate;
    private Role role;
}
