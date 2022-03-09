package com.netcracker.hb.entities;

import lombok.Data;

import java.util.Date;

@Data
public class PCard {
    private int num;
    private Date expireDate;
    private Role role;
}
