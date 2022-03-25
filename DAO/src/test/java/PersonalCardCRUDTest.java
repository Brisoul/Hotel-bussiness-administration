import com.netcracker.hb.Dao.CRUD.Person.IGuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.PersonalCardCRUD;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.util.UUID;
import org.junit.Test;

public class PersonalCardCRUDTest {
  IGuestCRUD<PersonalCard> personalCardIGuestCRUD = PersonalCardCRUD.getPersonalCardCRUD();


@Test
  public void whenSearchUUIDObjectReturnPersonalCard(){
    personalCardIGuestCRUD.searchUUIDObject(null);

  }
}
