package com.netcracker.hb.entities;

import lombok.Data;

import java.util.Date;


public class Employee extends Person{

    private Role role;

    public Employee(Role role){
        this.role = role;
    }



    @Data
    public class Contract{
        private Date beginContract;
        private Date endContract;
        private int salary;
        private int experience;
        private int workTime;
        

    }
}
