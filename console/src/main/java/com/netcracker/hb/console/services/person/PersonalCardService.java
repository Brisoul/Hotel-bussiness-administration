package com.netcracker.hb.console.services.person;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.GuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IEmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IGuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.PersonalCardCRUD;
import com.netcracker.hb.console.services.IPersonalCard;
import com.netcracker.hb.console.services.chekServeces.ValidationService;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.Person;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.util.Date;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class PersonalCardService implements IPersonalCard<PersonalCard> {

  private static IPersonalCard<PersonalCard> iPersonalCardService;

  private PersonalCardService() {
  }

  public static synchronized IPersonalCard<PersonalCard> getPersonalCardService() {
    if (iPersonalCardService == null) {
      iPersonalCardService = new PersonalCardService();
    }
    return iPersonalCardService;
  }

  private static final ValidationService validationService = ValidationService.getValidationService();
  private static final CRUD<PersonalCard> personalCardCRUD = PersonalCardCRUD.getPersonalCardCRUD();
  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();
  private static final IGuestCRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();

  private int numIterator() {
    int iterator = 0;
    for (PersonalCard personalCard : personalCardCRUD.searchObjects()) {
      if (iterator < personalCard.getNum()) {
        iterator = personalCard.getNum();
      }
    }
    return iterator + 1;
  }

  @Override
  public void addObjectPerson(Person person) {

    int cardNum = numIterator();
    PersonalCard personalCard = PersonalCard.builder()
        .uuid(UUID.randomUUID())
        .num(cardNum)
        .build();
    UUID id;
    Role role;
    if (person instanceof Guest) {
      Guest guest = (Guest) person;
      id = guest.getUuid();
      role = Role.GUEST;
      guest.setCardID(personalCard.getUuid());
      guestCRUD.saveObject(guest);
    } else {
      Employee employee = (Employee) person;
      id = employee.getUuid();
      role = employee.getRole();
      employee.setCardID(personalCard.getUuid());
      employeeCRUD.saveObject(employee);
    }
    personalCard.setRole(role);
    personalCard.setPersonID(id);

    personalCardCRUD.saveObject(personalCard);
  }

  @Override
  public void addObject() {

    int cardNum = numIterator();
    Person person = validationService.validationSearchPerson();
    UUID id;
    Role role;
    if (person instanceof Guest) {
      Guest guest = (Guest) person;
      id = guest.getUuid();
      role = Role.GUEST;
    } else {
      Employee employee = (Employee) person;
      id = employee.getUuid();
      role = employee.getRole();
    }

    PersonalCard personalCard = PersonalCard.builder()
        .personID(id)
        .role(role)
        .uuid(UUID.randomUUID())
        .num(cardNum)
        .build();

    personalCardCRUD.saveObject(personalCard);
  }


  @Override
  public void changeObject(PersonalCard object) {

    log.info("Start changing personal card " + personalCardCRUD.searchFileName(object));
    int userChoice;
    do {
      log.info("1.Set expire Date");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          log.info("write how many years do u wonna add to date expire(from 1970 :В)");
          long years = validationService.validationNumberChoice();
          years *= 360L * 86400000L;
          Date date = new Date(years);
          object.setExpireDate(date);
          personalCardCRUD.saveObject(object);
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
  public void displayObject(PersonalCard object) {
    log.info("_______________________");
    log.info("_______________________");
    log.info(personalCardCRUD.searchFileName(object));
    log.info("Card number " + object.getNum());
    log.info("Date expire " + object.getExpireDate());
    log.info("Role " + object.getRole());
    log.info("_______________________");
    log.info("_______________________");

  }
}
