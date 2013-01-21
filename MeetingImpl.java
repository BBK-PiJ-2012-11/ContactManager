import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	
	
	public MeetingImpl(Calendar date, Set<Contact> contacts) {
		//In order to create unique IDs, 
		//the hashCode of the date and the set of contacts is summed to create it. 
		this.id = date.hashCode()+contacts.hashCode();
		this.date = date;
		this.contacts = contacts;
	}

	//This constructor is made in case the id wants to be introduced manually
	//Useful for transforming a FutureMeeting into a PastMeeting and keeping the ID
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		this.id = date.hashCode()+contacts.hashCode();
		this.date = date;
		this.contacts = contacts;
	}

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
