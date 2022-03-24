package com.netcracker.hb.Dao.CRUD.Person;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.PersonCRUD;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.Person;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class PersonalCardCRUD implements CRUD<PersonalCard>, PersonCRUD<PersonalCard> {

  private static final CRUD<PersonalCard> personalCardCRUD = new PersonalCardCRUD();
  private PersonalCardCRUD() {
  }
  public static CRUD<PersonalCard> getPersonalCardCRUD() {
    return personalCardCRUD;
  }

  private final CRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();
  private final CRUD<Employee> employeeCRUD = EmployeeCRUD.getEmployeeCRUD();

  private final PersonCRUD<Guest> guestPersonCRUD = GuestCRUD.getGuestPersonCRUD();
  private final PersonCRUD<Employee> employeePersonCRUD = EmployeeCRUD.getEmployeePersonCRUD();


  @Override
  public PersonalCard searchObjectNameSurname(String name, String surname) {
    log.info("<Start searching card....");
    PersonalCard personalCard = null;
    Guest guest = guestPersonCRUD.searchObjectNameSurname(name, surname);
    Employee employee = employeePersonCRUD.searchObjectNameSurname(name, surname);
    if (guest == null && employee == null) {
      log.error("PERSON NOT FOUND");
    } else if (employee == null) {
      personalCard = searchUUIDObject(guest.getCardID());
      log.info("Person Guest with this card was found");
    } else if (guest == null) {
      personalCard = searchUUIDObject(employee.getCardID());
      log.info("Person Employee with this card was found");
    }
    //todo чето там про опшнл
    if (personalCard == null) {
      log.error("CARD NOT FOUND>");
    } else {
      log.info("card was found>");
    }
    return personalCard;
  }

  //Оно не должно работать
  @Override
  public boolean searchObjectRole(Role role) {
    return false;
  }

  @Override
  public PersonalCard searchObjectNum(int personalCardNum) {
    log.info("<Start searching personal card....");

    File personalCardFolderDirectory = new File(
        "entSAVE/personalcard_entities");
    String[] personalCardList = personalCardFolderDirectory.list();
    Object personalCard = null;
    try {
      for (String personalCardFolderName : personalCardList) {

        FileInputStream filePersonalCardIn = new FileInputStream(
            "entSAVE/personalcard_entities/" + personalCardFolderName);
        ObjectInputStream objectPersonalCardIn = new ObjectInputStream(filePersonalCardIn);
        PersonalCard object = (PersonalCard) objectPersonalCardIn.readObject();
        if (object.getNum() == personalCardNum) {
          log.info("Personal card was found>");
          personalCard = object;
        }
        objectPersonalCardIn.close();


      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (personalCard == null) {
        log.error("PERSONAL CARD NOT FOUND>");
      }
      return (PersonalCard) personalCard;
    }

  }

  @Override
  public PersonalCard searchUUIDObject(UUID uuid) {
    log.info("<Start searching personal card....");

    File personalCardFolderDirectory = new File(
        "entSAVE/personalcard_entities");
    String[] personalCardList = personalCardFolderDirectory.list();
    Object personalCard = null;
    try {
      for (String personalCardFolderName : personalCardList) {

        FileInputStream filePersonalCardIn = new FileInputStream(
            "entSAVE/personalcard_entities/" + personalCardFolderName);
        ObjectInputStream objectPersonalCardIn = new ObjectInputStream(filePersonalCardIn);
        PersonalCard object = (PersonalCard) objectPersonalCardIn.readObject();
        if (object.getUuid().equals(uuid)) {
          log.info("Personal card was found>");
          personalCard = object;
        }
        objectPersonalCardIn.close();


      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (personalCard == null) {
        log.error("PERSONAL CARD NOT FOUND>");
      }
      return (PersonalCard) personalCard;
    }
  }

  @Override
  public String searchFileName(PersonalCard personalCard) {
    log.info("<Start searching file name of personal card...");

    File personalCardFolderDirectory = new File("entSAVE/personalcard_entities");
    String[] personalCardList = personalCardFolderDirectory.list();
    String fileName = null;
    try {
      for (String personalCardFolderName : personalCardList) {

        FileInputStream filePersonalCardIn = new FileInputStream(
            "entSAVE/personalcard_entities/" + personalCardFolderName);
        ObjectInputStream objectPersonalCardIn = new ObjectInputStream(filePersonalCardIn);
        PersonalCard object = (PersonalCard) objectPersonalCardIn.readObject();
        if (object.equals(personalCard)) {
          log.info("File name of personal card was found>");
          fileName = personalCardFolderName;
        }
        objectPersonalCardIn.close();


      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (fileName == null) {
        log.error("FILE NAME OF PERSONAL CARD NOT FOUND>");
      }
      return fileName;
    }
  }

  @Override
  public void deleteObject(PersonalCard object) {

    //Отвязываем карту от пользователя
    PersonalCard personalCard = null;
    Guest guest = guestCRUD.searchUUIDObject(object.getUuid());
    Employee employee = employeeCRUD.searchUUIDObject(object.getUuid());
    if (guest == null && employee == null) {
      log.error("PERSON NOT FOUND");
    } else if (employee == null) {
      employee.setCardID(null);
      log.info("Person Guest with this card was found");
    } else if (guest == null) {
      guest.setCardID(null);
      log.info("Person Employee with this card was found");
    }

    //Удаляем карту
    File deleteFile = new File("entSAVE/guest_entities/" + searchFileName(object));
    if (deleteFile != null) {
      log.info("Guest was successfully deleted>");
      deleteFile.delete();
    } else {
      log.warn("NOTHING WAS DELETED, FILE GUEST NOT FOUND>");
    }
  }

  @Override
  public void saveObject(PersonalCard card) {
    log.info("<Start saving personal card...");

    try {
      FileOutputStream filePersonalCardOut = new FileOutputStream(
          "entSAVE/personalcard_entities/" + card.getUuid() + "-personalCard.txt");

      ObjectOutputStream objectPersonalCardOut = new ObjectOutputStream(filePersonalCardOut);
      objectPersonalCardOut.writeObject(card);
      objectPersonalCardOut.close();
      log.info("Success saving card>");

    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }
}
