package com.netcracker.hb.console;

import lombok.extern.log4j.Log4j;


@Log4j
public class Console {

  private static final InitializationManager initializationManager = new InitializationManager();
  private static final UtilizeManager utilizeManager = new UtilizeManager();

  public static void main(String[] args){

    utilizeManager.utilize();
    initializationManager.initialize();






    //    Scanner in = new Scanner(System.in);
//    int userChoice;
//    do {
//      log.info("1.Add Room");
//      log.info("2 Display all Rooms");
//      log.info("3 Search Room");
//      log.info("4 Delete Room");
//      log.info("5 Update Room");
//      log.info("0 Exit to previous menu");
//      log.info("Choose a number of menu:");
//      userChoice = in.nextInt();
//
//      switch (userChoice) {
//        case 1:
//        case 2:
//        case 3:
//        case 4:
//        case 5:
//        default:
//      }
//
//
//    } while (userChoice != 0);








  }

}
