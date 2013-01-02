import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest{
	
	Contact contact;
	
	@Before
	public void buildUp(){
		contact = new ContactImpl("Test Name",null);
	}
	
	@After
	public void cleanUp(){
		contact = null;
	}
	
	@Test
	public void testGetID(){		
		assertEquals("Test Name".hashCode(),contact.getId());
	}
	
	@Test
	public void testGetName(){
		assertEquals("Test Name",contact.getName());
	}
	
	@Test
	public void testGetNotes(){
		assertNull(contact.getNotes());
	}
	
	@Test
	public void  testAddNotes(){
		contact.addNotes("Test Notes");
		assertEquals("Test Notes",contact.getNotes());
	}
}	