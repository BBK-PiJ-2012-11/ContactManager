import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

public class ContactManagerImpl implements ContactManager{
	private Set<Contact> contacts;
	private List<FutureMeeting> futureMeetings;
	private List<PastMeeting> pastMeetings;
	final String FILENAME = "contacts.xml";
	
	public ContactManagerImpl(){
			XMLDecoder d = null;
			try{
				d = new XMLDecoder(new BufferedInputStream(new FileInputStream(FILENAME)));
				contacts = (Set<Contact>) d.readObject();
				futureMeetings = (List<FutureMeeting>) d.readObject();
				pastMeeting = (List<PastMeeting>) d.readObject();
			} catch (FileNetFoundException e){
				contacts = new HashSet<Contact>;
				futureMeetings = new ArrayList<FutureMeeting>;
				pastMeetings = new ArrayList<PastMeeting>;
			}
			d.close();
	}
	
	
	public int addFutureMeeting(Set<Contact> contacts, Calendar date){
		if(!this.contacts.containsAll(contacts) || (date.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) )
			throw new IllegalArgumentException ("The meeting is being tried to set in the past or any of the contacts is unknown/non-existent");
		FutureMeeting meeting = new FutureMeetingImpl(date,contacts);
		futureMeetings.add(meeting);
		return meeting.getId();
	}

	public PastMeeting getPastMeeting(int id){
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

	public FutureMeeting getFutureMeeting(int id){
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

	public List<Meeting> getFutureMeetingList(Contact contact){
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

	@SuppressWarnings("unchecked")
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<Meeting> unsortedList = new ArrayList<Meeting>();
		Iterator<PastMeeting> it1 = pastMeetings.iterator();
		while(it1.hasNext()){
			if(it1.next().getDate() == date)
				unsortedList.add(it1.next());
		}
		Iterator<FutureMeeting> it2 = futureMeetings.iterator();
		while(it2.hasNext()){
			if(it2.next().getDate() == date)
				unsortedList.add(it2.next());
		}
		return (List<Meeting>) sortListByDate(unsortedList);
	}
	
	@SuppressWarnings("unchecked")
	public List<PastMeeting> getPastMeetingList(Contact contact){
		if(!contacts.contains(contact)){
			throw new IllegalArgumentException ("The contact does not exist in the list of contacts");
		}
		List<Meeting> unsortedList = new ArrayList<Meeting>();
		Iterator<PastMeeting> it = pastMeetings.iterator();
		while (it.hasNext()){
			if (it.next().getContacts().contains(contact))
				unsortedList.add(it.next());
		}
		return (List<PastMeeting>) sortListByDate(unsortedList);
	}

	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text){
		if(contacts == null || date == null || text == null)
			throw new NullPointerException("Any of the arguments is null");
		if(contacts.isEmpty() || !this.contacts.containsAll(contacts))
			throw new IllegalArgumentException ("The list of contacts is empty or any of the contacts does not exist");
		this.pastMeetings.add(new PastMeetingImpl(date,contacts,text));
	}

	public void addMeetingNotes(int id, String text) {
		if(text==null)
			throw new NullPointerException ("The notes cannot be null");
		FutureMeeting futureMeeting = null;
		Iterator<FutureMeeting> it = futureMeetings.iterator();
		boolean foundId = false;
		while(it.hasNext()){
			if(it.next().getId()==id){
				foundId = true;
				futureMeeting = it.next();
				if(it.next().getDate().getTimeInMillis() > Calendar.getInstance().getTimeInMillis())
					throw new IllegalStateException ("This meeting cannot had been produced as it is set for a date in the future");
				break;
			}
		}
		if (!foundId)
			throw new IllegalArgumentException("The meeting Id introduced does not exist");
		pastMeetings.add(new PastMeetingImpl(futureMeeting,text));
	}
	
	public void addNewContact(String name, String notes){
		if (name == null || notes == null)
			throw new NullPointerException ("The name or the notes are null");
		contacts.add(new ContactImpl(name,notes));
	}

	public Set<Contact> getContacts(int... ids) {
		Set<Contact> foundContacts = new HashSet<Contact>();
		for (int id : ids){
			Iterator<Contact> it = contacts.iterator();
			boolean containsId = false;
			while(it.hasNext()){
				if (it.next().getId() == id){
					foundContacts.add(it.next());
					containsId = true;
				}
			}
			if (!containsId)
				throw new IllegalArgumentException ("Any of the IDs is not in the list");
		}
		return foundContacts;
	}


	public Set<Contact> getContacts(String name){
		if (name == null)
			throw new NullPointerException ("The parameter name cannot be null");
		Set<Contact> contactsWithName = new HashSet<Contact>();
		Iterator<Contact> it = contacts.iterator();
		while(it.hasNext()){
			if(it.next().getName().contains(name)){
				contactsWithName.add(it.next());
			}
		}
		return contactsWithName;
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}
	
	// This private method is used for sorting an unsorted List of Meetings by date. 
	// Uses restricted wildcards accepting only the class Meeting and its subclasses
	private  List<? extends Meeting> sortListByDate(List<? extends Meeting> unsortedList){
		List<Meeting> sortedList = new ArrayList<Meeting>();
		while(!unsortedList.isEmpty()){
			Iterator<? extends Meeting> it = unsortedList.iterator();
			long oldestDate = it.next().getDate().getTimeInMillis();
			Meeting oldestM = it.next();
			while(it.hasNext()){
				if(oldestDate > it.next().getDate().getTimeInMillis()){
					oldestDate = it.next().getDate().getTimeInMillis();
					oldestM = it.next();
				}								
			}
			unsortedList.remove(oldestM);
			sortedList.add(oldestM);
		}
		return sortedList;
	}

}
