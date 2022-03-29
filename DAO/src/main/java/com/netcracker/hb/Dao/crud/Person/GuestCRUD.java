package com.netcracker.hb.Dao.crud.Person;

import com.netcracker.hb.Dao.crud.CRUD;
import com.netcracker.hb.Dao.crud.DatabaseProperties;
import com.netcracker.hb.Dao.crud.hotel.RoomsCRUD;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j;

@Log4j
public class GuestCRUD implements IGuestCRUD<Guest> {

  private static IGuestCRUD<Guest> guestCRUD;

  private GuestCRUD() {
  }

  public static synchronized IGuestCRUD<Guest> getGuestCRUD() {
    if (guestCRUD == null) {
      guestCRUD = new GuestCRUD();
    }
    return guestCRUD;
  }

  private static final String START = "<Start searching guest....";
  private static final String END = "Guest was found>";
  private static final String ERROR = "Guest was not found>";

  private static final CRUD<Room> roomCRUD = RoomsCRUD.getRoomsCRUD();
  private static final IGuestCRUD<PersonalCard> personalCardCRUD = PersonalCardCRUD.getPersonalCardCRUD();

  @Override
  public Guest searchObjectNameSurname(String name, String surname) {
    log.info(START);

    File guestFolderDirectory = new File(DatabaseProperties.GUEST_CRUD_ENTITIES_PATH);
    String[] guestList = guestFolderDirectory.list();
    Guest guest = null;
    assert guestList != null;
    for (String guestFolderName : guestList) {
      try (
          FileInputStream fileGuestIn = new FileInputStream(
              DatabaseProperties.GUEST_CRUD_ENTITIES_PATH
                  + guestFolderName);
          ObjectInputStream objectGuestIn = new ObjectInputStream(fileGuestIn);
      ) {
        Guest object = (Guest) objectGuestIn.readObject();
        if (object.getSurname().equals(surname) && object.getName().equals(name)) {
          log.info(END);
          guest = object;
        }
      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (guest == null) {
      log.error(ERROR);
    }
    return guest;
  }

  @Override
  public boolean searchObjectRole(Role role) {
    return !searchObjects().isEmpty();
  }

  @Override
  public List<Guest> searchObjects() {
    log.info(START);
    File guestFolderDirectory = new File(DatabaseProperties.GUEST_CRUD_ENTITIES_PATH);
    String[] guestList = guestFolderDirectory.list();
    List<Guest> guests = new ArrayList<>();
    assert guestList != null;
    for (String guestFolderName : guestList) {
      try (
          FileInputStream fileGuestIn = new FileInputStream(
              DatabaseProperties.GUEST_CRUD_ENTITIES_PATH
                  + guestFolderName);
          ObjectInputStream objectGuestIn = new ObjectInputStream(fileGuestIn);
      ) {
        Guest object = (Guest) objectGuestIn.readObject();
        //
        guests.add(object);

      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (guests.isEmpty()) {
      log.error(ERROR);
    }
    return guests;
  }

  @Override
  public Guest searchObjectNum(int roomNum) {
    log.info(START);
    Room room = roomCRUD.searchObjectNum(roomNum);
    UUID uuid = null;
    for (UUID uuidStrange : room.getGuestID()) {
      uuid = uuidStrange;
    }
    // Если в комнате больше 1 гостя возвращает любого из живущих
    Guest guest = searchUUIDObject(uuid);
    if (guest == null || uuid == null) {
      log.error(END);
    } else {
      log.info(END);
    }
    return guest;
  }

  @Override
  public Guest searchUUIDObject(UUID uuid) {
    log.info(START);
    if (uuid == null) {
      return null;
    }
    File guestFolderDirectory = new File(DatabaseProperties.GUEST_CRUD_ENTITIES_PATH);
    String[] guestList = guestFolderDirectory.list();
    Guest guest = null;
    assert guestList != null;
    for (String guestFolderName : guestList) {
      try (
          FileInputStream fileGuestIn = new FileInputStream(
              DatabaseProperties.GUEST_CRUD_ENTITIES_PATH
                  + guestFolderName);
          ObjectInputStream objectGuestIn = new ObjectInputStream(fileGuestIn);
      ) {
        Guest object = (Guest) objectGuestIn.readObject();
        if (object != null && object.getUuid().equals(uuid)) {
          log.info(END);
          guest = object;
        }
      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (guest == null) {
      log.error(ERROR);
    }
    return guest;
  }

  @Override
  public String searchFileName(Guest object) {
    log.info(START);
    if (object == null) {
      return null;
    }
    File guestFolderDirectory = new File(DatabaseProperties.GUEST_CRUD_ENTITIES_PATH);
    String[] guestList = guestFolderDirectory.list();
    String fileName = null;
    assert guestList != null;
    for (String guestFolderName : guestList) {
      try (
          FileInputStream fileGuestIn = new FileInputStream(
              DatabaseProperties.GUEST_CRUD_ENTITIES_PATH
                  + guestFolderName);
          ObjectInputStream objectGuestIn = new ObjectInputStream(fileGuestIn);
      ) {
        Guest guest = (Guest) objectGuestIn.readObject();
        if (object.equals(guest)) {
          log.info(END);
          fileName = guestFolderName;
        }
      } catch (ClassNotFoundException | IOException exception) {
        exception.printStackTrace();
      }
    }
    if (fileName == null) {
      log.error(ERROR);
    }
    return fileName;
  }

  @Override
  public void deleteObject(Guest object) {

    //удаляем персональную карту
    if (object.getCardID() != null) {
      PersonalCard personalCard = personalCardCRUD.searchUUIDObject(object.getCardID());
      if (personalCard != null) {
        personalCardCRUD.deleteObject(personalCard);
      }
    }

    //убираем из комнаты
    if (object.getRoomID() != null) {
      Room room = roomCRUD.searchUUIDObject(object.getRoomID());
      if (room != null) {
        room.deleteGuest(object.getUuid());
        roomCRUD.saveObject(room);
      }
    }
    // удаляем
    Guest guest = searchUUIDObject(object.getUuid());
    File deleteFile = new File(
        DatabaseProperties.GUEST_CRUD_ENTITIES_PATH + searchFileName(guest));
    if (deleteFile.delete()) {
      log.info("Guest was successfully deleted>");
    } else {
      log.warn(ERROR);
    }
  }

  @Override
  public void saveObject(Guest guest) {
    log.info(START);

    try (
        FileOutputStream fileGuestOut = new FileOutputStream(
            DatabaseProperties.GUEST_CRUD_ENTITIES_PATH + guest.getUuid() + "-guest.txt");

        ObjectOutputStream objectGuestOut = new ObjectOutputStream(fileGuestOut);
    ) {
      objectGuestOut.writeObject(guest);
      log.info("Success saving guest>");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }
}
