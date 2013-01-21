import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager{
	private Set<Contact> contacts;
	private List<FutureMeeting> futureMeetings;
	private List<PastMeeting> pastMeetings;
	
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) throws IllegalArgumentException{
		if(!this.contacts.containsAll(contacts) || (date.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) )
			throw new IllegalArgumentException ("The meeting is being tried to set in the past or any of the contacts is unknown/non-existent");
		FutureMeeting meeting = new FutureMeetingImpl(date,contacts);
		futureMeetings.add(meeting);
		return meeting.getId();
	}

	public PastMeeting getPastMeeting(int id) throws IllegalArgumentException{
		Iterator<FutureMeeting> it1 = futureMeetings.iterator();
		while (it1.hasNext()){
			if (it1.next().getId() == id)
				throw new IllegalArgumentException("The ID introduced pertains to a Future Meeting");
		}
		Iterator<PastMeeting> it2 = pastMeetings.iterator();
		while (it1.hasNext()){
			if(it2.next().getId() == id)
				return it2.next();
		}
		return null;
	}

	public FutureMeeting getFutureMeeting(int id) throws IllegalArgumentException{
		Iterator<PastMeeting> it1 = pastMeetings.iterator();
		while (it1.hasNext()){
			if (it1.next().getId() == id)
				throw new IllegalArgumentException("The ID introduced pertains to a Past Meeting");
		}
		Iterator<FutureMeeting> it2 = futureMeetings.iterator();
		while (it1.hasNext()){
			if(it2.next().getId() == id)
				return it2.next();
		}
		return null;
	}

	public Meeting getMeeting(int id) {
		Iterator<PastMeeting> it1 = pastMeetings.iterator();
		while (it1.hasNext()){
			if (it1.next().getId() == id)
				return it1.next();
		}
		Iterator<FutureMeeting> it2 = futureMeetings.iterator();
		while (it1.hasNext()){
			if(it2.next().getId() == id)
				return it2.next();
		}
		return null;
	}

	public List<Meeting> getFutureMeetingList(Contact contact) throws IllegalArgumentException {
		if(!contacts.contains(contact))
			throw new IllegalArgumentException ("The contact does not exists");
		List<Meeting> meetingsWithContact = new ArrayList<Meeting>();
		Iterator<FutureMeeting> it = futureMeetings.iterator();
		while(it.hasNext()){
			if (it.next().getContacts().contains(contact))
				meetingsWithContact.add(it.next());				
		}
		return meetingsWithContact;
	}

	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String next) {
		// TODO Auto-generated method stub
		
	}

	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub
		
	}
	
	public void addNewContact(String name, String notes) {
		// TODO Auto-generated method stub
		
	}

	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Contact> getContacts(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}
	

}
