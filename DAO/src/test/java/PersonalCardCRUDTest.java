import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;

import com.netcracker.hb.dao.crud.person.IGuestCRUD;
import com.netcracker.hb.dao.crud.person.PersonalCardCRUD;
import com.netcracker.hb.entities.persons.PersonalCard;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class PersonalCardCRUDTest {

    PersonalCard personalCard;
    private IGuestCRUD<PersonalCard> personalCardIGuestCRUD = Mockito.mock(PersonalCardCRUD.class);

    UUID testUUID = UUID.randomUUID();


    @Before
    public void setUp() throws Exception {
        personalCard = PersonalCard.builder()
                .uuid(testUUID)
                .build();
    }

    @Test
    public void saveCard() {
        doNothing().when(personalCardIGuestCRUD).saveObject(personalCard);
        Mockito.when(personalCardIGuestCRUD.searchUUIDObject(testUUID)).thenReturn(personalCard);
        assertNotNull(personalCardIGuestCRUD.searchUUIDObject(testUUID));
    }

    @After
    public void afterCardDelete() {
        doNothing().when(personalCardIGuestCRUD).deleteObject(personalCard);
        Mockito.when(personalCardIGuestCRUD.searchUUIDObject(testUUID)).thenReturn(null);
        assertNull(personalCardIGuestCRUD.searchUUIDObject(testUUID));
    }
}
