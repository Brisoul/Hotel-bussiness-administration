package com.netcracker.hb.console.services.person;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.ContractCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IEmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IGuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.PersonalCardCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.IPersonalCard;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekServeces.ValidationService;
import com.netcracker.hb.console.services.hotel.RoomService;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Contract;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.util.HashSet;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class EmployeeService implements Service<Employee> {

  private static final Service<Employee> employeeService = new EmployeeService();

  private EmployeeService() {
  }

  public static Service<Employee> getEmployeeService() {
    return employeeService;
  }

  public ValidationService validationService = ValidationService.getValidationService();

  private static final IPersonalCard<PersonalCard> personalCardService = PersonalCardService.getPersonalCardService();
  private static final CRUD<Room> roomCRUD = RoomsCRUD.getRoomsCRUD();
  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();
  private static final IGuestCRUD<Contract> contractCRUD = ContractCRUD.getContractCRUD();
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
      log.info("7.Set username");
      log.info("7.Set password");
      log.info("8.Change card");
      log.info("9.Change contract");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          object.setName(validationService.validationName());
          employeeCRUD.saveObject(object);
          break;
        case 2:
          object.setSurname(validationService.validationName());
          employeeCRUD.saveObject(object);
          break;
        case 3:
          object.setAge(validationService.validationNumberChoice());
          employeeCRUD.saveObject(object);
          break;
        case 4:
          object.setSex(validationService.validationName());
          employeeCRUD.saveObject(object);
          break;
        case 5:
          if(object.getCardID()!=null){
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
          object.setUsername(validationService.validationName());
          employeeCRUD.saveObject(object);
          break;
        case 7:
          object.setPassword(validationService.validationName());
          employeeCRUD.saveObject(object);
          break;
        case 8:
          PersonalCard myCard = null;

          if (object.getCardID() == null) {
            for (PersonalCard personalCard : personalCardCRUD.searchObjects()) {
              if (personalCard.getPersonID().equals(object.getUuid())){
                myCard = personalCard;
                log.info("we find card with user uuid");
              }
            }

            if(myCard == null){
              personalCardService.addObjectPerson(object);
              log.info("Card not found, start creating card");
              for (PersonalCard personalCard : personalCardCRUD.searchObjects()) {
                if (personalCard.getPersonID().equals(object.getUuid())){
                  myCard = personalCard;
                  log.info("we find card with user uuid");
                }
              }
              log.info("Card was found");
              object.setCardID(myCard.getUuid());
              employeeCRUD.saveObject(object);

            } else {
              log.info("Card was found");
              object.setCardID(myCard.getUuid());
              employeeCRUD.saveObject(object);
            }

          }
          for (PersonalCard personalCard : personalCardCRUD.searchObjects()) {
            if (personalCard.getPersonID().equals(object.getUuid())){
              myCard = personalCard;
              log.info("we find card with user uuid");
            }
          }



          log.info("Card was found start changing...");
          personalCardService.changeObject(myCard);
          employeeCRUD.saveObject(object);
          break;
        case 9:
          Contract myContract = null;
          if (object.getContractID() == null) {
            for (Contract contract : contractCRUD.searchObjects()) {
              if (contract.getEmployeeID().equals(object.getUuid())){
                myContract = contract;
                log.info("we find contract with user uuid");
              }
            }
            if(myContract == null){
              contractService.addObjectPerson(object);
              log.info("contract not found, start creating contract");
              for (Contract contract : contractCRUD.searchObjects()) {
                if (contract.getEmployeeID().equals(object.getUuid())){
                  myContract = contract;
                  log.info("we find contract with user uuid");
                }
              }
              log.info("contract was found");
              object.setContractID(myContract.getUuid());
              employeeCRUD.saveObject(object);
            } else {
              log.info("contract was found");
              object.setContractID(myContract.getUuid());
              employeeCRUD.saveObject(object);
            }
          }

          for (Contract contract : contractCRUD.searchObjects()) {
            if (contract.getEmployeeID().equals(object.getUuid())){
              myContract = contract;
              log.info("we find contract with user uuid");
            }
          }

          log.info("Card was found start changing...");
          contractService.changeObject(myContract);


          employeeCRUD.saveObject(object);
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
  public void displayObject(Employee object) {
    log.info("_______________________");
    log.info("_______________________");
    log.info(employeeCRUD.searchFileName(object));
    log.info(object.getName() + "  " + object.getSurname());
    log.info("Возраст " + object.getAge());
    log.info("Пол " + object.getSex());
    log.info("Должность " + object.getRole());
    log.info("_______________________");
    if (object.getContractID() == null) {
      log.info("Контракт : не задан");
    } else {
      log.info("Контракт : " + contractCRUD.searchFileName(
          contractCRUD.searchUUIDObject(object.getContractID())));
    }
    log.info("_______________________");
    if (object.getCardID() == null) {
      log.info("Карта : не задана");
    } else {
      log.info("Карта : ");
      personalCardService.displayObject(personalCardCRUD.searchUUIDObject(object.getCardID()));
    }
    log.info("_______________________");
    log.info("_______________________");

  }


}
