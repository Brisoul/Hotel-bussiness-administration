package com.netcracker.hb.console.services.person;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.GuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.PersonalCardCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekServeces.ValidationService;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
import com.netcracker.hb.entities.persons.Person;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class PersonalCardService implements Service<PersonalCard> {

  private static final Service<PersonalCard> personalCardService = new PersonalCardService();

  private PersonalCardService() {
  }

  public static Service<PersonalCard> getPersonalCardService() {
    return personalCardService;
  }

  private static int cardNum;
  private final ValidationService validationService = ValidationService.getValidationService();
  private final CRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();
  private final CRUD<Employee> employeeCRUD = EmployeeCRUD.getEmployeeCRUD();
  private final CRUD<PersonalCard> personalCardCRUD = PersonalCardCRUD.getPersonalCardCRUD();


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

  }

  @Override
  public void displayObject(PersonalCard object) {

  }
}
