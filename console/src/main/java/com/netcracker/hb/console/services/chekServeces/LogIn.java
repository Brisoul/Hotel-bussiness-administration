package com.netcracker.hb.console.services.chekServeces;

import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.person.EmployeeService;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.Person;
import java.util.HashMap;
import java.util.UUID;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import java.util.Scanner;

@Log4j
public class LogIn {

  private static final LogIn logIn = new LogIn();
  private LogIn() {
  }
  public static LogIn getLogIn() {
    return logIn;
  }

  private static final ValidationService validationService = ValidationService.getValidationService();
  private static final Service<Employee> employeeService = EmployeeService.getEmployeeService();
  private static final EmployeeService employeeServiceMenu = EmployeeService.getEmployeeServiceMenu();

  Scanner in = new Scanner(System.in);


  public void startLogIn() {
    int userChoice;

    log.info("Start LogIn....");
    Employee employee = validationService.validationLogIn();
    do {
      log.info("Start LogIn....");
      log.info("Hello "+ employee.getName() + employee.getSurname()+"");
      log.info("1.Start work as " + employee.getRole());
      log.info("666 Back to main menu");
      log.info("Choose a number of menu:");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          log.info("1.Start work ....");
          switch (employee.getRole()){
            case ADMIN:
              employeeServiceMenu.adminMenu();
              break;
            case MANAGER:
              employeeServiceMenu.managerMenu();
              break;
            case SERVICE_EMPLOYEE:
              employeeServiceMenu.employeeMenu();
              break;
            case GUEST:
              log.error("guest cant use it");
              userChoice = 666;
              break;
          }
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
