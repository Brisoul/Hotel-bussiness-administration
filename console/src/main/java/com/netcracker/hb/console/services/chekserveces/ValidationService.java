package com.netcracker.hb.console.services.chekserveces;

import static java.lang.Character.isLetter;
import static java.lang.Character.isLetterOrDigit;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.GuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IEmployeeCRUD;
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

  private static ValidationService validationService;
  private ValidationService() {
  }
  public static synchronized ValidationService getValidationService() {
    if(validationService ==null){
      validationService = new ValidationService();
    }
    return validationService;
  }


  private final IGuestCRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();
  private final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();
  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();



  public int validationNumberChoice() {

    int num = 0;
    int correctChoice = 1;
    Scanner in = new Scanner(System.in);
    do {
      try {
        String numString = in.next();
        num = Integer.parseInt(numString);
      } catch (Exception exception){
        log.error("Cannot parse number, please try again",exception);
        continue;
      }
      if (num <= 0) {
        log.info("0 is not a good choice");
      } else if (num > 1000) {
        log.info("Dear user, our builders cant make it so big");
      } else {
        correctChoice = 0;
      }
    } while (correctChoice != 0);
    return num;

  }

  public boolean validationRole(Role mainRole, Role addedRole){
    //Сравниваем 2 роли, на допуск второй к первой.
    switch (mainRole){//равняем под главную роль
      case GUEST:
        return true;
      case SERVICE_EMPLOYEE:
        return addedRole != Role.GUEST;
      case MANAGER:
        return addedRole != Role.SERVICE_EMPLOYEE;
      case ADMIN:
        return addedRole != Role.MANAGER;
      default:
        break;
    }


    return false;
  }

  public Role validationRoleEmployeeChoice() {
    Role role;
    int i;
    int userChoice;
    do {
      log.info("Enter usage(Role)");
      log.info("1.Employee");
      log.info("2.Manager");
      log.info("3.Admin");
      Scanner in = new Scanner(System.in);
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
          log.info("Choose correct num");
          role = null;
      }
    } while (i != 1);

    return role;
  }

  public Role validationRoleChoice() {
    Role role;
    int i;
    int userChoice;
    do {
      log.info("Enter usage(Role)");
      log.info("1.Guest");
      log.info("2.Employee");
      log.info("3.Manager");
      log.info("4.Admin");
      Scanner in = new Scanner(System.in);
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
          log.info("Choose correct num");
          role = null;
      }
    } while (i != 1);

    return role;
  }

  public String validationName() {
    int correctChoice;
    char ch;

    String wordWithoutNumbers;
    Scanner in = new Scanner(System.in);
    do {
      correctChoice = 0;
      wordWithoutNumbers = in.nextLine();

      for (int i = 0; i < wordWithoutNumbers.length(); i++) {
        ch = wordWithoutNumbers.charAt(i);
        if (!isLetter(ch)) {
          correctChoice = 1;
        }
      }
      if (correctChoice == 1) {
        log.error("Word must have only letters");
      }

    } while (correctChoice != 0);
    return wordWithoutNumbers;
  }

  public String validationNameNum() {
    int correctChoice;
    char ch;

    String wordWithoutNumbers;
    Scanner in = new Scanner(System.in);
    do {
      correctChoice = 0;
      wordWithoutNumbers = in.nextLine();

      for (int i = 0; i < wordWithoutNumbers.length(); i++) {
        ch = wordWithoutNumbers.charAt(i);
        if (!isLetterOrDigit(ch)) {
          correctChoice = 1;
        }
      }
      if (correctChoice == 1) {
        log.error("Word must have only letters or numbers");
      }

    } while (correctChoice != 0);
    return wordWithoutNumbers;
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
        log.error("Person not found");
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
    return employeeCRUD.searchObjectNameSurname(name, surname);
  }

  public Guest validationSearchGuestNameSurname(){
    log.info("Name");
    String name = validationService.validationName();
    log.info("Surname");
    String surname = validationService.validationName();
    return guestCRUD.searchObjectNameSurname(name, surname);
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
      if(!floorCRUD.searchObjects().isEmpty()){
        return floorCRUD.searchObjects().size()>=num;
      } else{
        return false;
      }
  }

}
