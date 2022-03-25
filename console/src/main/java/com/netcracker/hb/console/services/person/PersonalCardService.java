package com.netcracker.hb.console.services.person;

import com.netcracker.hb.Dao.CRUD.CRUD;
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

  private static final IPersonalCard<PersonalCard> iPersonalCardService = new PersonalCardService();
  private PersonalCardService() {
  }
  public static IPersonalCard<PersonalCard> getPersonalCardService() {
    return iPersonalCardService;
  }

  private static int cardNum;
  private static final ValidationService validationService = ValidationService.getValidationService();
  private static final CRUD<PersonalCard> personalCardCRUD = PersonalCardCRUD.getPersonalCardCRUD();

  @Override
  public void addObjectPerson(Person person) {

    cardNum += 1;
    UUID ID = null;
    Role role = null;
    if (person instanceof Guest){
      Guest guest = (Guest)person;
      ID = guest.getUuid();
      role = Role.GUEST;
    } else{
      Employee employee = (Employee) person;
      ID = employee.getUuid();
      role = employee.getRole();
    }

    PersonalCard personalCard = PersonalCard.builder()
        .personID(ID)
        .role(role)
        .uuid(UUID.randomUUID())
        .num(cardNum)
        .build();

    personalCardCRUD.saveObject(personalCard);
  }

  @Override
  public void addObject() {

    cardNum += 1;
    Person person = validationService.validationSearchPerson();
    UUID ID = null;
    Role role = null;
    if (person instanceof Guest){
      Guest guest = (Guest)person;
      ID = guest.getUuid();
      role = Role.GUEST;
    } else{
      Employee employee = (Employee) person;
      ID = employee.getUuid();
      role = employee.getRole();
    }

    PersonalCard personalCard = PersonalCard.builder()
        .personID(ID)
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
          Long years = Long.valueOf(validationService.validationNumberChoice());
          years *= 360*86400000;
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
    log.info("Date expire "+object.getExpireDate());
    log.info("Role " + object.getRole());
    log.info("_______________________");
    log.info("_______________________");

  }
}
