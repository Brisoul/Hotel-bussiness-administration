package com.netcracker.hb.console.services.person;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.GuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IGuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.PersonalCardCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.IPersonalCard;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekServeces.ValidationService;
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
      log.info("5.Set room");
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
          log.info("Write room num");
          int roomNum = validationService.validationNumberChoice();
          Room room = roomCRUD.searchObjectNum(roomNum);
          if(validationService.validationRole(room.getRole(), object.getRole())){
            log.info("access is allowed");
            Set<UUID> guests = room.getGuestID();
            guests.add(object.getUuid());
            room.setGuestID(guests);
            roomCRUD.saveObject(room);
            object.setRoomID(room.getUuid());
            guestCRUD.saveObject(object);
          } else{
            log.error("access is denied");
            log.error("Error adding room");
          }
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
  public void displayObject(Guest object) {
    log.info("_______________________");
    log.info("_______________________");
    log.info(guestCRUD.searchFileName(object));
    log.info(object.getName() + "  " + object.getSurname());
    log.info("Age " + object.getAge());
    log.info("Sex " + object.getSex());
    log.info("Status " + object.getRole());
    log.info("_______________________");
    if (object.getRoomID() != null) {
      log.info("Number room: ");
      log.info(roomCRUD.searchUUIDObject(object.getRoomID()).getRoomNum());
    } else {
      log.info("not in room");
    }
    log.info("_______________________");
    if (object.getCardID() == null) {
      log.info("Card : not found");
    } else {
      log.info("Card : ");
      personalCardService.displayObject(personalCardCRUD.searchUUIDObject(object.getCardID()));
    }
    log.info("_______________________");
    log.info("_______________________");

  }
}
