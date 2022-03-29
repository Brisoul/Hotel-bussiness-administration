import com.netcracker.hb.Dao.crud.Person.IGuestCRUD;
import com.netcracker.hb.Dao.crud.Person.PersonalCardCRUD;
import com.netcracker.hb.entities.persons.PersonalCard;
import org.junit.Test;

public class PersonalCardCRUDTest {

  IGuestCRUD<PersonalCard> personalCardIGuestCRUD = PersonalCardCRUD.getPersonalCardCRUD();


  @Test
  public void whenSearchUUIDObjectReturnPersonalCard() {
    personalCardIGuestCRUD.searchUUIDObject(null);

  }
}
