package com.netcracker.hb.console.services.hotel;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.GuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IEmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IGuestCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.FloorCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekServeces.ValidationService;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class RoomService implements Service<Room> {

  private static final Service<Room> roomService = new RoomService();

  private RoomService() {
  }

  public static Service<Room> getRoomService() {
    return roomService;
  }


  public ValidationService validationService = ValidationService.getValidationService();
  public static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();
  public static final CRUD<Room> roomsCRUD = RoomsCRUD.getRoomsCRUD();
  private static final IGuestCRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();
  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();

  private static int roomNum;

  @Override
  public void addObject() {

    log.info("<Start creating room...");

    //Выбрали роль комнаты
    Role role = validationService.validationRoleChoice();
    //Выбрали этаж
    log.info("Enter floor on which the room is located ");
    int floorNum = validationService.validationNumberChoice();
    if (validationService.validationMaxNumber(floorNum)) {
      Floor floor = floorCRUD.searchObjectNum(floorNum);
      // TODO: 21.03.2022 можно ввести несуществующий этаж

      //Выбрали номер комнаты
      roomNum += 1;

      //Создали комнату
      Room room = Room.builder()
          .floorId(floor.getUuid())
          .roomNum(roomNum)
          .guestID(new HashSet<>())
          .employeeID(new HashSet<>())
          .role(role)
          .uuid(UUID.randomUUID())
          .build();
      roomsCRUD.saveObject(room);

      //закидываем в хешсет флура этаж
      Set roomsID = floor.getRoomsID();
      roomsID.add(room.getUuid());
      floor.setRoomsID(roomsID);
      floorCRUD.saveObject(floor);

      log.info("End creating room>");
    } else {
      log.error("ERROR CREATING ROOM>");
    }

  }

  @Override
  public void changeObject(Room object) {
    log.info("Start changing room " + roomsCRUD.searchFileName(object));
    int userChoice;
    do {
      log.info("1.Set room num(not work)");
      log.info("2.Set floor num(not work)");
      log.info("3.Set role(that will delete all persons connections)");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          log.info("U CANT CHANGE ROOM NUM, BUILD ANOTHER ROOM");
          break;
        case 2:
          log.info("U CANT CHANGE FLOOR NUM, BUILD ANOTHER ROOM");
          break;
        case 3:
          for (UUID uuidEmployee : object.getEmployeeID()) {
            Employee employee = employeeCRUD.searchUUIDObject(uuidEmployee);
            employee.deleteRoomsID(object.getUuid());
            employeeCRUD.saveObject(employee);
          }
          for (UUID uuidGuest : object.getGuestID()) {
            Guest guest = guestCRUD.searchUUIDObject(uuidGuest);
            guest.setRoomID(null);
            guestCRUD.saveObject(guest);
          }
          object.setRole(validationService.validationRoleChoice());
          roomsCRUD.saveObject(object);
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

  @Override
  public void displayObject(Room object) {
    log.info("_______________________");
    log.info("_______________________");
    log.info(roomsCRUD.searchFileName(object));
    log.info("Номер комнаты : " + object.getRoomNum());
    log.info("_______________________");
    log.info("Количество гостей : " + object.getEmployeeID().size());
    log.info("Гости : ");
    for (UUID uuid : object.getGuestID()) {
      Guest guest = guestCRUD.searchUUIDObject(uuid);
      log.info(guest.getSurname() + " " + guest.getName());
    }
    log.info("_______________________");
    log.info("Количество работников : " + object.getEmployeeID().size());
    log.info("Работники : ");
    for (UUID uuid : object.getEmployeeID()) {
      Employee employee = employeeCRUD.searchUUIDObject(uuid);
      log.info(employee.getSurname() + " " + employee.getName());
    }
    log.info("_______________________");
    log.info("_______________________");

  }

}
