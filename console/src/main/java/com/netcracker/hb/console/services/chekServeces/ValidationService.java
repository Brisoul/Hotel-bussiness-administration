package com.netcracker.hb.console.services.chekServeces;

import static java.lang.Character.isLetter;
import static java.lang.Character.isLetterOrDigit;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.IEmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.GuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IGuestCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.FloorCRUD;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.Person;
import java.util.Scanner;
import lombok.extern.log4j.Log4j;


@Log4j
public class ValidationService {

  private static final ValidationService validationService = new ValidationService();
  private ValidationService() {
  }
  public static ValidationService getValidationService() {
    return validationService;
  }


  private final IGuestCRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();
  private final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();
  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();


  Scanner in = new Scanner(System.in);


  public int validationNumberChoice() {

    int Num, correctChoice;
    Scanner in = new Scanner(System.in);
    do {
      Num = in.nextInt();
      if (Num <= 0) {
        log.info("0 is not a good choice");
        correctChoice = 1;
      } else if (Num > 10000) {
        log.info("Dear user, our builders cant make it so big");
        correctChoice = 1;
      } else {
        correctChoice = 0;
      }
    } while (correctChoice != 0);
    return Num;

  }

  public boolean validationRole(Role mainRole, Role addedRole){
    //Сравниваем 2 роли, на допуск второй к первой.
    switch (mainRole){//равняем под главную роль
      case GUEST:
        return true;
      case SERVICE_EMPLOYEE:
        if(addedRole == Role.GUEST){
          return false;
        } else{
          return true;
        }
      case MANAGER:
        if(addedRole == Role.SERVICE_EMPLOYEE){
          return false;
        } else{
          return true;
        }
      case ADMIN:
        if(addedRole == Role.MANAGER){
          return false;
        } else{
          return true;
        }
      default:
        break;
    }


    return false;
  }

  public Role validationRoleEmployeeChoice() {
    Role role;
    int i, userChoice;
    do {
      log.info("Enter usage(Role)");
      log.info("1.Employee");
      log.info("2.Manager");
      log.info("3.Admin");
      userChoice = in.nextInt();
      switch (userChoice) {
        case 1:
          role = Role.SERVICE_EMPLOYEE;
          i = 1;
          break;
        case 2:
          role = Role.MANAGER;
          i = 1;
          break;
        case 3:
          role = Role.ADMIN;
          i = 1;
          break;
        default:
          i = 0;
          log.info("choose correct num");
          role = null;
      }
    } while (i != 1);

    return role;
  }

  public Role validationRoleChoice() {
    Role role;
    int i, userChoice;
    do {
      log.info("Enter usage(Role)");
      log.info("1.Guest");
      log.info("2.Employee");
      log.info("3.Manager");
      log.info("4.Admin");
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
    } while (i != 1);

    return role;
  }

  public String validationName() {
    int correctChoice;
    char ch;

    String wordWithoutNums;
    Scanner in = new Scanner(System.in);
    do {
      correctChoice = 0;
      wordWithoutNums = in.nextLine();

      for (int i = 0; i < wordWithoutNums.length(); i++) {
        ch = wordWithoutNums.charAt(i);
        if (!isLetter(ch)) {
          correctChoice = 1;
        }
      }
      if (correctChoice == 1) {
        log.error("Word must have only letters");
      }

    } while (correctChoice != 0);
    return wordWithoutNums;
  }

  public String validationNameNum() {
    int correctChoice;
    char ch;

    String wordWithoutNums;
    Scanner in = new Scanner(System.in);
    do {
      correctChoice = 0;
      wordWithoutNums = in.nextLine();

      for (int i = 0; i < wordWithoutNums.length(); i++) {
        ch = wordWithoutNums.charAt(i);
        if (!isLetterOrDigit(ch)) {
          correctChoice = 1;
        }
      }
      if (correctChoice == 1) {
        log.error("Word must have only letters or numbers");
      }

    } while (correctChoice != 0);
    return wordWithoutNums;
  }

  public Person validationSearchPerson() {
    Person person;
    do {
      person = null;
      log.info("Name");
      String name = validationService.validationName();
      log.info("Surname");
      String surname = validationService.validationName();
      Guest guest = guestCRUD.searchObjectNameSurname(name, surname);
      Employee employee = employeeCRUD.searchObjectNameSurname(name, surname);
      if (guest == null && employee == null) {
        log.error("PERSON NOT FOUND");
      } else if (employee == null) {
        person = guest;
        log.info("Person Guest was found");
      } else if (guest == null) {
        person = employee;
        log.info("Person Employee was found");
      }
    } while (person == null);
    return person;
  }

  public Employee validationSearchEmployeeNameSurname(){
    log.info("Name");
    String name = validationService.validationName();
    log.info("Surname");
    String surname = validationService.validationName();
    Employee employee = employeeCRUD.searchObjectNameSurname(name, surname);
    return employee;
  }

  public Guest validationSearchGuestNameSurname(){
    log.info("Name");
    String name = validationService.validationName();
    log.info("Surname");
    String surname = validationService.validationName();
    Guest guest = guestCRUD.searchObjectNameSurname(name, surname);
    return guest;
  }

  public Employee validationLogIn(){
    Employee employee;
    do {
      log.info("Username ");
      String username = validationService.validationNameNum();
      log.info("Password");
      String password = validationService.validationNameNum();

      employee = employeeCRUD.searchObjectLogIn(username, password);
      if (employee == null) {
        log.info("User not found, try again");
      } else {
        log.info("U successfully logged in");
      }
    } while (employee== null);
    return employee;
  }


  public boolean validationMaxNumber(int num){
    if(floorCRUD.searchObjects()!= null){
      if(floorCRUD.searchObjects().size()>=num){
        return true;
      } else{
        return false;
      }
    } else {
      return false;
    }
  }



}
