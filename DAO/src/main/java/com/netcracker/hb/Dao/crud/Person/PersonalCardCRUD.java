package com.netcracker.hb.Dao.crud.Person;

import com.netcracker.hb.Dao.crud.DatabaseProperties;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.persons.Employee;
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
public class PersonalCardCRUD implements IGuestCRUD<PersonalCard> {

  private static IGuestCRUD<PersonalCard> personalCardCRUD;

  private PersonalCardCRUD() {
  }

  public static synchronized IGuestCRUD<PersonalCard> getPersonalCardCRUD() {
    if (personalCardCRUD == null) {
      personalCardCRUD = new PersonalCardCRUD();
    }
    return personalCardCRUD;
  }

  private static final String START = "<Start searching personal card....";
  private static final String END = "Personal card was found>";
  private static final String ERROR = "Personal card was not found>";

  private final IGuestCRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();
  private final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();


  @Override
  public PersonalCard searchObjectNameSurname(String name, String surname) {
    log.info(START);

    File personalCardFolderDirectory = new File(
        DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH);
    String[] personalCardList = personalCardFolderDirectory.list();
    PersonalCard personalCard = null;
    if (personalCardList == null) {
      return null;
    }
    for (String personalCardFolderName : personalCardList) {
      try (
          FileInputStream filePersonalCardIn = new FileInputStream(
              DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH + personalCardFolderName);
          ObjectInputStream objectPersonalCardIn = new ObjectInputStream(filePersonalCardIn);
      ) {
        PersonalCard object = (PersonalCard) objectPersonalCardIn.readObject();
        if (object != null) {
          if (object.getRole() == Role.GUEST) {
            Guest guest = guestCRUD.searchUUIDObject(object.getPersonID());
            if (guest.getName().equals(name) && guest.getSurname().equals(surname)) {
              log.info(END);
              personalCard = object;
            }
          } else {
            Employee employee = employeeCRUD.searchUUIDObject(object.getPersonID());
            if (employee.getName().equals(name) && employee.getSurname().equals(surname)) {
              log.info(END);
              personalCard = object;
            }
          }
        }
      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (personalCard == null) {
      log.error(ERROR);
    }
    return personalCard;

  }


  @Override
  public boolean searchObjectRole(Role role) {
    return guestCRUD.searchObjectRole(role);
  }

  @Override
  public List<PersonalCard> searchObjects() {
    log.info(START);

    File personalCardFolderDirectory = new File(
        DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH);
    String[] personalCardList = personalCardFolderDirectory.list();
    List<PersonalCard> personalCards = new ArrayList<>();
    if (personalCardList == null) {
      return null;
    }
    for (String personalCardFolderName : personalCardList) {
      try (
          FileInputStream filePersonalCardIn = new FileInputStream(
              DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH + personalCardFolderName);
          ObjectInputStream objectPersonalCardIn = new ObjectInputStream(filePersonalCardIn)
      ) {
        PersonalCard object = (PersonalCard) objectPersonalCardIn.readObject();
        personalCards.add(object);
      } catch (ClassNotFoundException | IOException exception) {
        exception.printStackTrace();
      }
    }
    if (personalCards.isEmpty()) {
      log.error(ERROR);
    }
    return personalCards;
  }

  @Override
  public PersonalCard searchObjectNum(int personalCardNum) {
    log.info(START);
    File personalCardFolderDirectory = new File(
        DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH);
    String[] personalCardList = personalCardFolderDirectory.list();
    PersonalCard personalCard = null;
    if (personalCardList == null) {
      return null;
    }
    for (String personalCardFolderName : personalCardList) {
      try (
          FileInputStream filePersonalCardIn = new FileInputStream(
              DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH + personalCardFolderName);
          ObjectInputStream objectPersonalCardIn = new ObjectInputStream(filePersonalCardIn);
      ) {
        PersonalCard object = (PersonalCard) objectPersonalCardIn.readObject();
        if (object.getNum() == personalCardNum) {
          log.info(END);
          personalCard = object;
        }
      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (personalCard == null) {
      log.error(ERROR);
    }
    return personalCard;

  }

  @Override
  public PersonalCard searchUUIDObject(UUID uuid) {
    log.info(START);
    if (uuid == null) {
      return null;
    }

    File personalCardFolderDirectory = new File(
        DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH);
    String[] personalCardList = personalCardFolderDirectory.list();
    PersonalCard personalCard = null;
    if (personalCardList == null) {
      return null;
    }
    for (String personalCardFolderName : personalCardList) {
      try (
          FileInputStream filePersonalCardIn = new FileInputStream(
              DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH + personalCardFolderName);
          ObjectInputStream objectPersonalCardIn = new ObjectInputStream(filePersonalCardIn);
      ) {
        PersonalCard object = (PersonalCard) objectPersonalCardIn.readObject();
        if (object != null && object.getUuid().equals(uuid)) {
          log.info(END);
          personalCard = object;

        }
      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (personalCard == null) {
      log.error(ERROR);
    }
    return personalCard;
  }

  @Override
  public String searchFileName(PersonalCard personalCard) {
    log.info(START);

    File personalCardFolderDirectory = new File(
        DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH);
    String[] personalCardList = personalCardFolderDirectory.list();
    String fileName = null;
    if (personalCardList == null) {
      return null;
    }
    for (String personalCardFolderName : personalCardList) {
      try (
          FileInputStream filePersonalCardIn = new FileInputStream(
              DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH + personalCardFolderName);
          ObjectInputStream objectPersonalCardIn = new ObjectInputStream(filePersonalCardIn);
      ) {
        PersonalCard object = (PersonalCard) objectPersonalCardIn.readObject();
        if (object.equals(personalCard)) {
          log.info(END);
          fileName = personalCardFolderName;
        }
      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (fileName == null) {
      log.error(ERROR);
    }
    return fileName;
  }

  @Override
  public void deleteObject(PersonalCard object) {

    //Отвязываем карту от пользователя
    Guest guest = guestCRUD.searchUUIDObject(object.getPersonID());
    Employee employee = employeeCRUD.searchUUIDObject(object.getPersonID());
    if (guest == null && employee == null) {
      log.error("Person not found its strange");
    } else if (employee == null) {
      guest.setCardID(null);
      log.info("Person Guest with this card was found");
    } else if (guest == null) {
      employee.setCardID(null);
      log.info("Person Employee with this card was found");
    }

    //Удаляем карту
    File deleteFile = new File(
        DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH + searchFileName(object));
    if (deleteFile.delete()) {
      log.info("Personal card was successfully deleted>");
    } else {
      log.warn(ERROR);
    }
  }

  @Override
  public void saveObject(PersonalCard card) {
    log.info("<Start saving personal card...");

    try (
        FileOutputStream filePersonalCardOut = new FileOutputStream(
            DatabaseProperties.PERSONAL_CARD_CRUD_ENTITIES_PATH + card.getUuid()
                + "-personalCard.txt");
        ObjectOutputStream objectPersonalCardOut = new ObjectOutputStream(filePersonalCardOut);
    ) {
      objectPersonalCardOut.writeObject(card);
      log.info("Success saving card>");

    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }
}
