public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting{

	public FutureMeetingImpl(Meeting meeting) {
		super(meeting.getId(), meeting.getDate(), meeting.getContacts());
	}
	
	

}
