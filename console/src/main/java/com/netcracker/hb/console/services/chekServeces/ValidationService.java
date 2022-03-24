package com.netcracker.hb.console.services.chekServeces;

import static java.lang.Character.isLetter;
import static java.lang.Character.isLetterOrDigit;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.GuestCRUD;
import com.netcracker.hb.Dao.CRUD.PersonCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.person.GuestService;
import com.netcracker.hb.console.services.person.PersonalCardService;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.Person;
import java.util.Scanner;
import java.util.UUID;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;


@Log4j
public class ValidationService {

  private static final ValidationService validationService = new ValidationService();
  private ValidationService() {
  }
  public static ValidationService getValidationService() {
    return validationService;
  }

  private final CRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();
  private final CRUD<Employee> employeeCRUD = EmployeeCRUD.getEmployeeCRUD();
  private final PersonCRUD<Guest> guestPersonCRUD = GuestCRUD.getGuestPersonCRUD();
  private final PersonCRUD<Employee> employeePersonCRUD = EmployeeCRUD.getEmployeePersonCRUD();
  private static final EmployeeCRUD employeeLogIn = EmployeeCRUD.getEmployeeLogIn();


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

  public Role validationRoleChoice() {
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
    } while (i != 1);

    return role;
  }

  public void validationGuestRoleCheck() {

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
      String name = validationService.validationName();
      String surname = validationService.validationName();
      Guest guest = guestPersonCRUD.searchObjectNameSurname(name, surname);
      Employee employee = employeePersonCRUD.searchObjectNameSurname(name, surname);
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

  public Employee validationLogIn(){
    Employee employee;
    do {
      log.info("Username ");
      String username = validationService.validationNameNum();
      log.info("Password");
      String password = validationService.validationNameNum();

      employee = employeeLogIn.searchObjectLogIn(username, password);
      if (employee == null) {
        log.info("User not found, try again");
      } else {
        log.info("U successfully logged in");
      }
    } while (employee== null);
    return employee;
  }


}
