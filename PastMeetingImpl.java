public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	
	private String notes;

	public PastMeetingImpl(Meeting meeting,	String notes) {
		super(meeting.getId(), meeting.getDate(), meeting.getContacts());
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}



}
