package com.netcracker.hb.console.services.person;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.GuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.PersonalCardCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekServeces.ValidationService;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class GuestService implements Service<Guest> {

  private static final Service<Guest> guestService = new GuestService();
  private GuestService(){}
  public static Service<Guest> getGuestService(){
    return guestService;
  }

  public ValidationService validationService = ValidationService.getValidationService();
  private static final CRUD<Room> roomCRUD = RoomsCRUD.getRoomsCRUD();
  private static final CRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();

  @Override
  public void addObject() {

    log.info("<Start creating guest...");
    //Создали гостя
    Guest guest = Guest.builder()
        .build();
    guest.setRole(Role.GUEST);
    guest.setUuid(UUID.randomUUID());
    guest.setName(validationService.validationName());
    guest.setSurname(validationService.validationName());
    guestCRUD.saveObject(guest);

    //Сохранили
    log.info("End creating guest>");

  }

  @Override
  public void changeObject(Guest object) {

  }

  @Override
  public void displayObject(Guest object) {

  }
}
