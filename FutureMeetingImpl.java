import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable{

	public FutureMeetingImpl(Calendar date, Set<Contact> contacts){
		super(date, contacts);
	}
	
	public FutureMeetingImpl(int ID, Calendar date, Set<Contact> contacts){
		super(ID, date, contacts);
	}
	

}
