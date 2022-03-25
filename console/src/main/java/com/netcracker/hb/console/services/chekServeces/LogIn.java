package com.netcracker.hb.console.services.chekServeces;

import com.netcracker.hb.console.services.MenuServices;
import com.netcracker.hb.entities.persons.Employee;
import lombok.extern.log4j.Log4j;

@Log4j
public class LogIn {

  private static final LogIn logIn = new LogIn();

  private LogIn() {
  }

  public static LogIn getLogIn() {
    return logIn;
  }

  private static final ValidationService validationService = ValidationService.getValidationService();
  private static final MenuServices menuServices = MenuServices.getMenuServices();

  public void startLogIn() {
    int userChoice;

    log.info("Start LogIn....");
    Employee employee = validationService.validationLogIn();
    do {
      log.info("Start LogIn....");
      log.info("Hello " + employee.getName() + employee.getSurname() + "");
      log.info("1.Start work as " + employee.getRole());
      log.info("666 Back to main menu");
      log.info("Choose a number of menu:");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          log.info("Start work ....");
          switch (employee.getRole()) {
            case ADMIN:
              menuServices.adminMenu();
              break;
            case MANAGER:
              menuServices.managerMenu();
              break;
            case SERVICE_EMPLOYEE:
              menuServices.employeeMenu();
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
