package com.netcracker.hb.console.services;

import com.netcracker.hb.entities.Role;
import java.util.Scanner;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;


@Log4j
public class ValidationService {
  Scanner in = new Scanner(System.in);


  // TODO: 21.03.2022 зарефакторить названия методов
  public int correctNumberChoice() {

    int Num, correctChoice;
    Scanner in = new Scanner(System.in);
    do {
      log.info("write number");
      Num = in.nextInt();
      if (Num <= 0) {
        log.info("0 is not a good choice");
        correctChoice = 0;
      } else if (Num > 10000) {
        log.info("Dear user, our builders cant make it so big");
        correctChoice = 0;
      } else {
        correctChoice = 1;
      }
    } while (correctChoice != 1);
    return Num;

  }

  public Role correctRoleChoice(){
    Role role;
    int i, userChoice;
    do {
      log.info("Enter usage(Role)"
          + "1.Guest"
          + "2.Employee"
          + "3.Manager"
          + "4.Admin");
      userChoice = in.nextInt();
      switch (userChoice) {
        case 1:
          role = Role.GUEST;
          i = 1;
          break;
        case 2:
          role = Role.SERVICE_EMPLOYEE;
          i = 1;
          break;
        case 3:
          role = Role.MANAGER;
          i = 1;
          break;
        case 4:
          role = Role.ADMIN;
          i = 1;
          break;
        default:
          i = 0;
          log.info("choose correct num");
          role = null;
      }
    } while(i != 1);

    return role;
  }

}
