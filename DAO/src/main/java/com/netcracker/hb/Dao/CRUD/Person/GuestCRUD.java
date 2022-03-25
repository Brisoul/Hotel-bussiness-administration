package com.netcracker.hb.Dao.CRUD.Person;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.RoomsCRUD;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

  private static final IGuestCRUD<Guest> guestCRUD = new GuestCRUD();
  private GuestCRUD() {
  }

  public static IGuestCRUD<Guest> getGuestCRUD() {
    return guestCRUD;
  }

  private static final CRUD<Room> roomCRUD = RoomsCRUD.getRoomsCRUD();
  private static final CRUD<PersonalCard> personalCardCRUD = PersonalCardCRUD.getPersonalCardCRUD();

  @Override
  public Guest searchObjectNameSurname(String name, String surname) {
    log.info("<Start searching guest....");

    File guestFolderDirectory = new File("entSAVE/guest_entities");
    String[] guestList = guestFolderDirectory.list();
    Guest guest = null;
    try {
      for (String guestFolderName : guestList) {
        FileInputStream fileGuestIn = new FileInputStream("entSAVE/guest_entities/"
            + guestFolderName);
        ObjectInputStream objectGuestIn = new ObjectInputStream(fileGuestIn);
        Guest object = (Guest) objectGuestIn.readObject();
        //
        if (object.getSurname().equals(surname) && object.getName().equals(name)) {
          log.info("guest was found>");
          guest = object;
        }
        objectGuestIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (guest == null) {
        log.error("GUEST NOT FOUND>");
      }
      return guest;
    }
  }

  @Override
  public boolean searchObjectRole(Role role) {
    return false;
  }

  @Override
  public List<Guest> searchObjects() {
    log.info("<Start searching guests....");
    File guestFolderDirectory = new File("entSAVE/guest_entities");
    String[] guestList = guestFolderDirectory.list();
    List guests = new ArrayList();
    try {
      for (String guestFolderName : guestList) {
        FileInputStream fileGuestIn = new FileInputStream("entSAVE/guest_entities/"
            + guestFolderName);
        ObjectInputStream objectGuestIn = new ObjectInputStream(fileGuestIn);
        Guest object = (Guest) objectGuestIn.readObject();
        //
        guests.add(object);
        objectGuestIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (guests.isEmpty()) {
        log.error("GUESTS NOT FOUND>");
      }
      return guests;
    }
  }

  @Override
  public Guest searchObjectNum(int roomNum) {
    log.info("<Start searching guest....");
    Room room = roomCRUD.searchObjectNum(roomNum);
    UUID uuid = null;
    for (UUID uuidStrange : room.getGuestID()) {
      uuid = uuidStrange;
    }

    Guest guest = searchUUIDObject(uuid);
    //TODO можно добавить выбор пользователя по найденным в комнате людям
    if (guest == null || uuid == null) {
      log.error("GUEST NOT FOUND>");
    } else{
      log.info("guest was found>");
    }
    return guest;
  }

  @Override
  public Guest searchUUIDObject(UUID uuid) {
    log.info("<Start searching guest....");
    File guestFolderDirectory = new File("entSAVE/guest_entities");
    String[] guestList = guestFolderDirectory.list();
    Guest guest = null;
    try {
      for (String guestFolderName : guestList) {
        FileInputStream fileGuestIn = new FileInputStream("entSAVE/guest_entities/"
            + guestFolderName);
        ObjectInputStream objectGuestIn = new ObjectInputStream(fileGuestIn);
        Guest object = (Guest) objectGuestIn.readObject();
        //
        if (object.getUuid().equals(uuid)) {
          log.info("guest was found>");
          guest = object;
        }
        objectGuestIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (guest == null) {
        log.error("GUEST NOT FOUND>");
      }
      return guest;
    }
  }

  @Override
  public String searchFileName(Guest object) {
    log.info("<Start searching file name of guest...");
    File guestFolderDirectory = new File("entSAVE/guest_entities");
    String[] guestList = guestFolderDirectory.list();
    String fileName = null;
    try {
      for (String guestFolderName : guestList) {
        FileInputStream fileGuestIn = new FileInputStream("entSAVE/guest_entities/"
            + guestFolderName);
        ObjectInputStream objectGuestIn = new ObjectInputStream(fileGuestIn);
        Guest guest = (Guest) objectGuestIn.readObject();
        //
        if (object.equals(guest)) {
          log.info("File name of guest was found>");
          fileName = guestFolderName;
        }
        objectGuestIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (fileName == null) {
        log.error("FILE NAME OF GUEST NOT FOUND>");
      }
      return fileName;
    }
  }

  @Override
  public void deleteObject(Guest object) {
    //удаляем персональную карту
    PersonalCard personalCard = personalCardCRUD.searchUUIDObject(object.getCardID());
    personalCardCRUD.deleteObject(personalCard);

    //убираем из комнаты
    Room room = roomCRUD.searchUUIDObject(object.getRoomID());
    room.deleteGuest(object.getUuid());
    roomCRUD.saveObject(room);
    // удаляем
    Guest guest = searchUUIDObject(object.getUuid());

    File deleteFile = new File("entSAVE/guest_entities/"+ searchFileName(guest));
    if (deleteFile != null) {
      log.info("Guest was successfully deleted>" );
      deleteFile.delete();
    } else{
      log.warn("NOTHING WAS DELETED, FILE GUEST NOT FOUND>");
    }
  }

  @Override
  public void saveObject(Guest guest) {
    log.info("<Start saving guest...");

    try {
      FileOutputStream fileGuestOut = new FileOutputStream(
          "entSAVE/guest_entities/" + guest.getUuid() + "-guest.txt");

      ObjectOutputStream objectGuestOut = new ObjectOutputStream(fileGuestOut);
      objectGuestOut.writeObject(guest);
      objectGuestOut.close();
      log.info("Success saving guest>");

    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }
}
