package com.netcracker.hb.console.services.person;

import com.netcracker.hb.dao.crud.CRUD;
import com.netcracker.hb.dao.crud.person.GuestCRUD;
import com.netcracker.hb.dao.crud.person.IGuestCRUD;
import com.netcracker.hb.dao.crud.person.PersonalCardCRUD;
import com.netcracker.hb.dao.crud.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.IPersonalCard;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekserveces.ValidationService;
import com.netcracker.hb.console.services.hotel.RoomService;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.util.Set;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class GuestService implements Service<Guest> {

  private static Service<Guest> guestService;

  private GuestService() {
  }

  public static synchronized Service<Guest> getGuestService() {
    if(guestService==null){
      guestService = new GuestService();
    }
    return guestService;
  }

  private static final ValidationService validationService = ValidationService.getValidationService();
  private static final IPersonalCard<PersonalCard> personalCardService = PersonalCardService.getPersonalCardService();
  private static final CRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();
  private static final IGuestCRUD<PersonalCard> personalCardCRUD = PersonalCardCRUD.getPersonalCardCRUD();
  private static final CRUD<Room> roomCRUD = RoomsCRUD.getRoomsCRUD();
  private static final Service<Room> roomService = RoomService.getRoomService();

  @Override
  public void addObject() {

    log.info("<Start creating guest...");
    //Создали гостя
    Guest guest = Guest.builder()
        .build();
    guest.setRole(Role.GUEST);
    guest.setUuid(UUID.randomUUID());
    log.info("Write name");
    guest.setName(validationService.validationName());
    log.info("Write surname");
    guest.setSurname(validationService.validationName());
    guestCRUD.saveObject(guest);

    //Сохранили
    log.info("End creating guest>");

  }

  @Override
  public void changeObject(Guest object) {
    log.info("Start changing employee " + guestCRUD.searchFileName(object));
    int userChoice;
    do {
      log.info("1.Set name");
      log.info("2.Set surname");
      log.info("3.Set sex");
      log.info("4.Change card");
      log.info("5.Change room");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          log.info("name:");
          object.setName(validationService.validationName());
          guestCRUD.saveObject(object);
          break;
        case 2:
          log.info("surname:");
          object.setSurname(validationService.validationName());
          guestCRUD.saveObject(object);
          break;
        case 3:
          log.info("sex:");
          object.setSex(validationService.validationName());
          guestCRUD.saveObject(object);
          break;
        case 4:
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
              guestCRUD.saveObject(object);
            }
          }

          for (PersonalCard personalCard : personalCardCRUD.searchObjects()) {
            if (personalCard.getPersonID().equals(object.getUuid())) {
              myCard = personalCard;
            }
          }
          personalCardService.changeObject(myCard);
          guestCRUD.saveObject(object);
          break;
        case 5:
          changeRoom(object);
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
  public void displayObject(Guest object) {
    final String BORDER = "_______________________";
    log.info(BORDER);
    log.info(BORDER);
    log.info(guestCRUD.searchFileName(object));
    log.info(object.getName() + "  " + object.getSurname());
    log.info("Age " + object.getAge());
    log.info("Sex " + object.getSex());
    log.info("Status " + object.getRole());
    log.info(BORDER);
    if (object.getRoomID() != null) {
      log.info("Number room: ");
      log.info(roomCRUD.searchUUIDObject(object.getRoomID()).getRoomNum());
    } else {
      log.info("not in room");
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

  private void changeRoom(Guest object) {
    log.info("Start changing room ");
    int userChoice;
    int roomNum;
    Room room;
    do {
      log.info("1.Display room");
      log.info("2.Add room");
      log.info("3.Delete room");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
            room = roomCRUD.searchUUIDObject(object.getRoomID());
            if(room != null) {
              roomService.displayObject(room);
            } else{
              log.info("Undefined room");
            }
          break;
        case 2:
          log.info("Write room num");
          roomNum = validationService.validationNumberChoice();
          room = roomCRUD.searchObjectNum(roomNum);
          if (room!=null && validationService.validationRole(room.getRole(), object.getRole())) {

            log.info("access is allowed");
            object.setRoomID(room.getUuid());
            guestCRUD.saveObject(object);

            Set<UUID> roomSet = room.getGuestID();
            roomSet.add(object.getUuid());
            room.setGuestID(roomSet);
            roomCRUD.saveObject(room);
          } else {
            log.error("access is denied");
            log.error("Error adding room");
          }
          break;
        case 3:
          room = roomCRUD.searchUUIDObject(object.getRoomID());
          if (room != null) {
            //удаляем айдишник комнаты из списка работника
            object.setRoomID(null);
            guestCRUD.saveObject(object);

            //удаляем айдишник работника из списка комнаты
            room.deleteEmployee(object.getUuid());
            roomCRUD.saveObject(room);

          } else {
            log.error("Room not found error removing room");
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
}
