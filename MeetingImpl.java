import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	
	
	public int getId() {
		return id;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public Set<Contact> getContacts() {
		return contacts;
	}
	
	
	
	
	
}
