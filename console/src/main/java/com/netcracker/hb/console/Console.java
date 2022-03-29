package com.netcracker.hb.console;

import com.netcracker.hb.console.services.chekServeces.LogIn;
import com.netcracker.hb.console.services.chekServeces.ValidationService;
import lombok.extern.log4j.Log4j;


@Log4j
public class Console {


  private static final InitializationManager initializationManager = InitializationManager.getInitializationManager();
  private static final ValidationService validationService = ValidationService.getValidationService();
  private static final LogIn logIn = LogIn.getLogIn();

  public static void main(String[] args) {
    initializationManager.initializationDirectory();
    initializationManager.initializeHotel();
    initializationManager.initializeAdmin();

    log.info("Start work...");
    int userChoice;
    do {
      log.info("Hello Guest, please logIn to start work with hotel");
      log.info("1.Start LogIn");
      log.info("2.Registration");
      log.info("666 Exit");
      log.info("Choose a number of menu:");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          logIn.startLogIn();
          break;
        case 2:
          log.error("ONLY ADMINISTRATOR CAN ADD NEW USERS");
          break;
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
