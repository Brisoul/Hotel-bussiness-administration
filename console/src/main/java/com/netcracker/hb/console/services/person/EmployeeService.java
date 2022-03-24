package com.netcracker.hb.console.services.person;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekServeces.ValidationService;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
import java.util.HashSet;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class EmployeeService implements Service<Employee> {

  private static final Service<Employee> employeeService = new EmployeeService();
  private static final EmployeeService employeeServiceMenu = new EmployeeService();
  private EmployeeService(){}
  public static Service<Employee> getEmployeeService(){
    return employeeService;
  }
  public static EmployeeService getEmployeeServiceMenu() {
    return employeeServiceMenu;
  }

  public ValidationService validationService = ValidationService.getValidationService();
  private static final CRUD<Room> roomCRUD = RoomsCRUD.getRoomsCRUD();
  private static final CRUD<Employee> employeeCRUD = EmployeeCRUD.getEmployeeCRUD();

  @Override
  public void addObject() {

    log.info("Enter username");
    String username = validationService.validationNameNum();
    log.info("Enter password");
    String password = validationService.validationNameNum();
    log.info("<Start creating employee...");
    Employee employee = Employee.builder()
        .username(username)
        .password(password)
        .roomsID(new HashSet<>())
        .build();

    employee.setUuid(UUID.randomUUID());
    employee.setRole(validationService.validationRoleChoice());
    log.info("Enter Name");
    employee.setName(validationService.validationName());
    log.info("Enter Surname");
    employee.setSurname(validationService.validationName());
    employeeCRUD.saveObject(employee);

    log.info("End creating employee>");

  }

  @Override
  public void changeObject(Employee object) {


  }

  @Override
  public void displayObject(Employee object) {

  }


  public void adminMenu(){
    log.info("Start work as Admin");
    int userChoice;
    do {
      log.info("1.Work with floors");
      log.info("2.Work with rooms");
      log.info("3.Work with employers");
      log.info("4.Work with cards");
      log.info("5.Work with guests");
      log.info("666.Exit");
      log.info("Choose a number of menu:");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 666:
          log.info("see u!");
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);

  }

  public void managerMenu(){
    log.info("Start work as manager");
    int userChoice;
    do {
      log.info("1.Work with employers");
      log.info("2.Work with cards");
      log.info("3.Work with guests");
      log.info("0 Exit");
      log.info("Choose a number of menu:");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
        case 2:
        case 666:
          log.info("see u!");
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);

  }
  public void employeeMenu(){log.info("Start work as manager");
    int userChoice;
    do {
      log.info("1.Work with cards");
      log.info("2.Work with guests");
      log.info("0 Exit");
      log.info("Choose a number of menu:");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
        case 2:
        case 666:
          log.info("see u!");
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);

  }
}
