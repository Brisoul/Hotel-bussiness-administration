package com.netcracker.hb.console.services;


import com.netcracker.hb.dao.crud.CRUD;
import com.netcracker.hb.dao.crud.person.EmployeeCRUD;
import com.netcracker.hb.dao.crud.person.GuestCRUD;
import com.netcracker.hb.dao.crud.person.IEmployeeCRUD;
import com.netcracker.hb.dao.crud.person.IGuestCRUD;
import com.netcracker.hb.dao.crud.hotel.FloorCRUD;
import com.netcracker.hb.dao.crud.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.chekserveces.ValidationService;
import com.netcracker.hb.console.services.hotel.FloorService;
import com.netcracker.hb.console.services.hotel.RoomService;
import com.netcracker.hb.console.services.person.EmployeeService;
import com.netcracker.hb.console.services.person.GuestService;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
import lombok.extern.log4j.Log4j;

@Log4j
public class MenuServices {

  private static MenuServices menuServices ;

  private MenuServices() {
  }

  public static synchronized MenuServices getMenuServices() {
    if(menuServices ==null){
      menuServices = new MenuServices();
    }
    return menuServices;
  }
  
  private static final ValidationService validationService = ValidationService.getValidationService();
  private static final Service<Floor> floorService = FloorService.getFloorService();
  private static final Service<Room> roomService = RoomService.getRoomService();
  private static final Service<Employee> employeeService = EmployeeService.getEmployeeService();
  private static final Service<Guest> guestService = GuestService.getGuestService();
  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();
  private static final CRUD<Room> roomCRUD = RoomsCRUD.getRoomsCRUD();
  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();
  private static final IGuestCRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();

  public void floorsWorkMenu() {
    log.info("Start work with floors");
    int userChoice;
    int floorNum;
    do {
      log.info("1.Display all floors");
      log.info("2.Search floor(Num)");
      log.info("3.Add floor");
      log.info("4.Delete floor(Num)");
      log.info("5.Change floor(Num)");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          for (Floor floor : floorCRUD.searchObjects()) {
            floorService.displayObject(floor);
          }
          break;
        case 2:
          log.info("Write floor num");
          floorNum = validationService.validationNumberChoice();
          if (floorCRUD.searchObjectNum(floorNum) != null) {
            floorService.displayObject(floorCRUD.searchObjectNum(floorNum));
          } else {
            log.error("Undefined room");
          }
          break;
        case 3:
          floorService.addObject();
          break;
        case 4:
          log.info("Write floor num");
          floorNum = validationService.validationNumberChoice();
          if (floorCRUD.searchObjectNum(floorNum) != null) {
            floorCRUD.deleteObject(floorCRUD.searchObjectNum(floorNum));
          } else {
            log.error("Undefined floor ");
          }
          break;
        case 5:
          log.info("Write floor num");
          floorNum = validationService.validationNumberChoice();
          if (floorCRUD.searchObjectNum(floorNum) != null) {
            floorService.changeObject(floorCRUD.searchObjectNum(floorNum));
          } else {
            log.error("Undefined floor ");
          }
          break;
        case 666:
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);
  }

  public void roomsWorkMenu() {
    log.info("Start work with rooms");
    int userChoice;
    int roomNum;
    do {
      log.info("1.Display all rooms");
      log.info("2.Search room(Num)");
      log.info("3.Add room");
      log.info("4.Delete room(Num)");
      log.info("5.Change room(Num)");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          for (Room room : roomCRUD.searchObjects()) {
            roomService.displayObject(room);
          }
          break;
        case 2:
          log.info("Write roomNum");
          roomNum = validationService.validationNumberChoice();
          if (roomCRUD.searchObjectNum(roomNum) != null) {
            roomService.displayObject(roomCRUD.searchObjectNum(roomNum));
          } else {
            log.error("Undefined room ");
          }
          break;
        case 3:
          roomService.addObject();
          break;
        case 4:
          log.info("Write roomNum");
          roomNum = validationService.validationNumberChoice();
          if (roomCRUD.searchObjectNum(roomNum) != null) {
            roomCRUD.deleteObject(roomCRUD.searchObjectNum(roomNum));
          } else {
            log.error("Undefined room ");
          }
          break;
        case 5:
          log.info("Write roomNum");
          roomNum = validationService.validationNumberChoice();
          if (roomCRUD.searchObjectNum(roomNum) != null) {
            roomService.changeObject(roomCRUD.searchObjectNum(roomNum));
          } else {
            log.error("Undefined room ");
          }
          break;
        case 666:
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);
  }

  public void employersWorkMenu() {
    log.info("Start work with employee");
    int userChoice;
    Employee object;
    do {
      log.info("1.Display all employee");
      log.info("2.Search employee(Name Surname)");
      log.info("3.Add employee");
      log.info("4.Delete employee(Name Surname)");
      log.info("5.Change employee(Name Surname)");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          for (Employee employee : employeeCRUD.searchObjects()) {
            employeeService.displayObject(employee);
          }
          break;
        case 2:
          Employee employee = validationService.validationSearchEmployeeNameSurname();
          if (employee != null) {
            employeeService.displayObject(employee);
          } else {
            log.error("Undefined employee ");
          }
          break;
        case 3:
          employeeService.addObject();
          break;
        case 4:
          object = validationService.validationSearchEmployeeNameSurname();
          if (object != null) {
            employeeCRUD.deleteObject(object);
          } else {
            log.error("Undefined employee ");
          }
          break;
        case 5:
          object = validationService.validationSearchEmployeeNameSurname();
          if (object != null) {
            employeeService.changeObject(object);
          } else {
            log.error("Undefined employee ");
          }
          break;
        case 666:
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);
  }

  public void guestsWorkMenu() {
    log.info("Start work with guests");
    int userChoice;
    do {
      log.info("1.Display all guests");
      log.info("2.Search guest(Name Surname)");
      log.info("3.Add guests");
      log.info("4.Delete guests(Name Surname)");//не работает
      log.info("5.Change guest(Name Surname)");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          for (Guest guest : guestCRUD.searchObjects()) {
            guestService.displayObject(guest);
          }
          break;
        case 2:
          Guest guest = validationService.validationSearchGuestNameSurname();
          if (guest != null) {
            guestService.displayObject(guest);
          } else {
            log.error("Undefined guest");
          }
          break;
        case 3:
          guestService.addObject();
          break;
        case 4:
          Guest object = validationService.validationSearchGuestNameSurname();
          if (object != null) {
            guestCRUD.deleteObject(object);
          } else {
            log.error("Undefined guest");
          }
          break;
        case 5:
          object = validationService.validationSearchGuestNameSurname();
          if (object != null) {
            guestService.changeObject(object);
          } else {
            log.error("Undefined guest");
          }
          break;
        case 666:
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);
  }

  public void adminMenu() {
    log.info("Start work as Admin");
    int userChoice;
    do {
      log.info("1.Work with floors");
      log.info("2.Work with rooms");
      log.info("3.Start work as manager");
      log.info("4.Start work as service employee");
      log.info("666.Exit");
      log.info("Choose a number of menu:");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          floorsWorkMenu();
          break;
        case 2:
          roomsWorkMenu();
          break;
        case 3:
          managerMenu();
          break;
        case 4:
          employeeMenu();
          break;
        case 666:
          log.info("See u!");
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);

  }

  public void managerMenu() {
    log.info("Start work as manager");
    int userChoice;
    do {
      log.info("1.Work with employers");
      log.info("2.Start work as service employee");
      log.info("666 Exit");
      log.info("Choose a number of menu:");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          employersWorkMenu();
          break;
        case 2:
          employeeMenu();
          break;
        case 666:
          log.info("See u!");
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);

  }

  public void employeeMenu() {
    log.info("Start work as employee");
    int userChoice;
    do {
      log.info("1.Work with guests");
      log.info("666 Exit");
      log.info("Choose a number of menu:");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          guestsWorkMenu();
          break;
        case 666:
          log.info("See u!");
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);

  }
}
