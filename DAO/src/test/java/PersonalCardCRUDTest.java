import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.netcracker.hb.dao.crud.person.IGuestCRUD;
import com.netcracker.hb.dao.crud.person.PersonalCardCRUD;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonalCardCRUDTest {

  IGuestCRUD<PersonalCard> personalCardIGuestCRUD = PersonalCardCRUD.getPersonalCardCRUD();
  UUID testUUID = UUID.randomUUID();



  @Before
  public void beforeCardInit() {
    PersonalCard personalCard = PersonalCard.builder().uuid(testUUID).build();
    assertNotNull(personalCard);
    assertNotNull(personalCard.getUuid());
    personalCardIGuestCRUD.saveObject(personalCard);
    assertNotNull(personalCardIGuestCRUD.searchUUIDObject(testUUID));
  }
  // TODO: 05.04.2022 тут пока не проходит тест ибо оно не сохраняет в нужную сущность, думаю замочить но це не точно 

  @Test
  public void testSearchUUIDObjectPersonalCard() {
    assertNull(personalCardIGuestCRUD.searchUUIDObject(null));
  }

  @After
  public void afterCardDelete() {
    personalCardIGuestCRUD.deleteObject(personalCardIGuestCRUD.searchUUIDObject(testUUID));
    assertNull(personalCardIGuestCRUD.searchUUIDObject(testUUID));
  }

}
