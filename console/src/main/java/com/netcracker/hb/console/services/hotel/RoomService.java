package com.netcracker.hb.console.services.hotel;

import com.netcracker.hb.dao.crud.CRUD;
import com.netcracker.hb.dao.crud.person.EmployeeCRUD;
import com.netcracker.hb.dao.crud.person.GuestCRUD;
import com.netcracker.hb.dao.crud.person.IEmployeeCRUD;
import com.netcracker.hb.dao.crud.person.IGuestCRUD;
import com.netcracker.hb.dao.crud.hotel.FloorCRUD;
import com.netcracker.hb.dao.crud.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekserveces.ValidationService;
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

  private static Service<Room> roomService ;

  private RoomService() {
  }

  public static synchronized Service<Room> getRoomService() {
    if(roomService ==null){
      roomService = new RoomService();
    }
    return roomService;
  }


  public static final ValidationService validationService = ValidationService.getValidationService();
  public static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();
  public static final CRUD<Room> roomsCRUD = RoomsCRUD.getRoomsCRUD();
  private static final IGuestCRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();
  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();


  private int numIterator(){
    int iterator = 0;
    for(Room room :roomsCRUD.searchObjects()){
      if(iterator< room.getRoomNum()){
        iterator =room.getRoomNum();
      }
    }
    return iterator+1;
  }


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

      //Создали комнату
      int roomNum = numIterator();
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
      Set<UUID> roomsID = floor.getRoomsID();
      roomsID.add(room.getUuid());
      floor.setRoomsID(roomsID);
      floorCRUD.saveObject(floor);

      log.info("End creating room>");
    } else {
      log.error("Error creating room>");
    }

  }

  @Override
  public void changeObject(Room object) {
    log.info("Start changing room " + roomsCRUD.searchFileName(object));
    int userChoice;
    do {
      log.info("1.Set role(that will delete all persons connections)");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
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
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);

  }

  @Override
  public void displayObject(Room object) {
    final String BORDER = "_______________________";
    log.info(BORDER);
    log.info(BORDER);
    log.info(roomsCRUD.searchFileName(object));
    log.info("Room num : " + object.getRoomNum());
    log.info(BORDER);
    log.info("Guests qun : " + object.getEmployeeID().size());
    log.info("Guests : ");
    for (UUID uuid : object.getGuestID()) {
      Guest guest = guestCRUD.searchUUIDObject(uuid);
      log.info(guest.getSurname() + " " + guest.getName());
    }
    log.info(BORDER);
    log.info("Employers : " + object.getEmployeeID().size());
    log.info("Employers : ");
    for (UUID uuid : object.getEmployeeID()) {
      Employee employee = employeeCRUD.searchUUIDObject(uuid);
      log.info(employee.getSurname() + " " + employee.getName());
    }
    log.info(BORDER);
    log.info(BORDER);

  }

}
