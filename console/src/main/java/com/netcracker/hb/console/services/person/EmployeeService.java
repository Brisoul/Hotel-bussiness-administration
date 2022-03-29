package com.netcracker.hb.console.services.person;

import com.netcracker.hb.Dao.crud.CRUD;
import com.netcracker.hb.Dao.crud.Person.ContractCRUD;
import com.netcracker.hb.Dao.crud.Person.IContractCRUD;
import com.netcracker.hb.Dao.crud.Person.IEmployeeCRUD;
import com.netcracker.hb.Dao.crud.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.crud.Person.IGuestCRUD;
import com.netcracker.hb.Dao.crud.Person.PersonalCardCRUD;
import com.netcracker.hb.Dao.crud.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.IPersonalCard;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekserveces.ValidationService;
import com.netcracker.hb.console.services.hotel.RoomService;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Contract;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class EmployeeService implements Service<Employee> {

  private static Service<Employee> employeeService;

  private EmployeeService() {
  }

  public static synchronized Service<Employee> getEmployeeService() {
    if (employeeService == null) {
      employeeService = new EmployeeService();
    }
    return employeeService;
  }

  public static final ValidationService validationService = ValidationService.getValidationService();
  private static final IPersonalCard<PersonalCard> personalCardService = PersonalCardService.getPersonalCardService();
  private static final CRUD<Room> roomCRUD = RoomsCRUD.getRoomsCRUD();
  private static final Service<Room> roomService = RoomService.getRoomService();
  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();
  private static final IContractCRUD<Contract> contractCRUD = ContractCRUD.getContractCRUD();
  private static final IGuestCRUD<PersonalCard> personalCardCRUD = PersonalCardCRUD.getPersonalCardCRUD();
  private static final IPersonalCard<Contract> contractService = ContractService.getContractService();

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
    employee.setRole(validationService.validationRoleEmployeeChoice());
    log.info("Enter Name");
    employee.setName(validationService.validationName());
    log.info("Enter Surname");
    employee.setSurname(validationService.validationName());
    employeeCRUD.saveObject(employee);

    log.info("End creating employee>");

  }

  @Override
  public void changeObject(Employee object) {
    log.info("Start changing employee " + employeeCRUD.searchFileName(object));
    int userChoice;
    do {
      log.info("1.Set name");
      log.info("2.Set surname");
      log.info("3.Set age");
      log.info("4.Set sex");
      log.info("5.Set role(this will delete all room connections and personal card))");
      log.info("6.Set username");
      log.info("7.Set password");
      log.info("8.Change card");
      log.info("9.Change contract");
      log.info("10.Change room list");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          log.info("name");
          object.setName(validationService.validationName());
          employeeCRUD.saveObject(object);
          break;
        case 2:
          log.info("surname");
          object.setSurname(validationService.validationName());
          employeeCRUD.saveObject(object);
          break;
        case 3:
          log.info("age");
          object.setAge(validationService.validationNumberChoice());
          employeeCRUD.saveObject(object);
          break;
        case 4:
          log.info("sex");
          object.setSex(validationService.validationName());
          employeeCRUD.saveObject(object);
          break;
        case 5:
          if (object.getCardID() != null) {
            personalCardCRUD.deleteObject(personalCardCRUD.searchUUIDObject(object.getCardID()));
          }
          for (UUID uuidRoom : object.getRoomsID()) {
            Room room = roomCRUD.searchUUIDObject(uuidRoom);
            room.deleteEmployee(object.getUuid());
            roomCRUD.saveObject(room);
          }
          object.setRole(validationService.validationRoleEmployeeChoice());
          employeeCRUD.saveObject(object);
          break;
        case 6:
          log.info("username");
          object.setUsername(validationService.validationName());
          employeeCRUD.saveObject(object);
          break;
        case 7:
          log.info("password");
          object.setPassword(validationService.validationName());
          employeeCRUD.saveObject(object);
          break;
        case 8:
          PersonalCard myCard = null;
          if (object.getCardID() == null) {
            for (PersonalCard personalCard : personalCardCRUD.searchObjects()) {
              if (personalCard.getPersonID().equals(object.getUuid())) {
                myCard = personalCard;
              }
            }
            if (myCard == null) {
              personalCardService.addObjectPerson(object);

              for (PersonalCard personalCard : personalCardCRUD.searchObjects()) {
                if (personalCard.getPersonID().equals(object.getUuid())) {
                  myCard = personalCard;
                }
              }
            } else {
              object.setCardID(myCard.getUuid());
              employeeCRUD.saveObject(object);
            }
          }

          for (PersonalCard personalCard : personalCardCRUD.searchObjects()) {
            if (personalCard.getPersonID().equals(object.getUuid())) {
              myCard = personalCard;
            }
          }
          personalCardService.changeObject(myCard);
          employeeCRUD.saveObject(object);
          break;

        case 9:
          Contract myContract = null;
          if (object.getContractID() == null) {
            for (Contract contract : contractCRUD.searchObjects()) {
              if (contract.getEmployeeID().equals(object.getUuid())) {
                myContract = contract;
              }
            }
            if (myContract == null) {
              contractService.addObjectPerson(object);
              log.info("contract not found, start creating contract");
              for (Contract contract : contractCRUD.searchObjects()) {
                if (contract.getEmployeeID().equals(object.getUuid())) {
                  myContract = contract;
                }
              }
            }
            log.info("contract was found");
            object.setContractID(myContract.getUuid());
            employeeCRUD.saveObject(object);
          }

          for (Contract contract : contractCRUD.searchObjects()) {
            if (contract.getEmployeeID().equals(object.getUuid())) {
              myContract = contract;
              log.info("we find contract with user uuid");
            }
          }

          log.info("Card was found start changing...");
          contractService.changeObject(myContract);

          employeeCRUD.saveObject(object);
          break;
        case 10:
          changeRoomList(object);
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

  @Override
  public void displayObject(Employee object) {
    final String BORDER = "_______________________";
    log.info(BORDER);
    log.info(BORDER);
    log.info(employeeCRUD.searchFileName(object));
    log.info(object.getName() + "  " + object.getSurname());
    log.info("Age " + object.getAge());
    log.info("Sex " + object.getSex());
    log.info("Post " + object.getRole());
    log.info(BORDER);
    log.info("Rooms access " + object.getRoomsID().size());
    log.info("Rooms numbers ");
    for (UUID uuid : object.getRoomsID()) {
      Room room = roomCRUD.searchUUIDObject(uuid);
      log.info(room.getRoomNum());
    }
    log.info(BORDER);
    if (object.getContractID() == null) {
      log.info("Contract : not found");
    } else {
      log.info("Contract : ");
      contractService.displayObject(contractCRUD.searchUUIDObject(object.getContractID()));
    }
    log.info(BORDER);
    if (object.getCardID() == null) {
      log.info("Card : not found");
    } else {
      log.info("Card : ");
      personalCardService.displayObject(personalCardCRUD.searchUUIDObject(object.getCardID()));
    }
    log.info(BORDER);
    log.info(BORDER);

  }


  private void changeRoomList(Employee object) {
    log.info("Start changing room List ");
    int userChoice;
    int roomNum;
    Room room;
    do {
      log.info("1.Display room list");
      log.info("2.Add room");
      log.info("3.Delete room");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          if(!object.getRoomsID().isEmpty()) {
            for (UUID uuid : object.getRoomsID()) {
              room = roomCRUD.searchUUIDObject(uuid);
              roomService.displayObject(room);
            }
          }else{
            log.info("Undefined room");
          }
          break;
        case 2:
          log.info("Write room num");
          roomNum = validationService.validationNumberChoice();
          room = roomCRUD.searchObjectNum(roomNum);
          if (room != null && validationService.validationRole(room.getRole(), object.getRole())) {
            log.info("access is allowed");
            Set<UUID> employers = room.getEmployeeID();
            employers.add(object.getUuid());
            room.setEmployeeID(employers);
            roomCRUD.saveObject(room);

            Set<UUID> roomSet = object.getRoomsID();
            roomSet.add(room.getUuid());
            object.setRoomsID(roomSet);
            employeeCRUD.saveObject(object);
          } else {
            log.error("access is denied");
            log.error("Error adding room");
          }
          break;
        case 3:
          log.info("Write room num");
          roomNum = validationService.validationNumberChoice();
          room = roomCRUD.searchObjectNum(roomNum);
          if (room != null) {
            //удаляем айдишник комнаты из списка работника
            object.deleteRoomsID(room.getUuid());
            employeeCRUD.saveObject(object);

            //удаляем айдишник работника из списка комнаты
            room.deleteEmployee(object.getUuid());
            roomCRUD.saveObject(room);

          } else {
            log.error("Room not found error removing room");
          }
          break;
        default:
          log.error("Choose correct num");
          break;
      }
    } while (userChoice != 666);

  }

}
