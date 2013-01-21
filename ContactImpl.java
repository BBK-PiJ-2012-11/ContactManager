public class ContactImpl implements Contact{
	private int ID;
	private String name;
	private String notes;
	
	ContactImpl(String name,String notes){
		//To create a unique ID, it is the result of summing the name and the notes hashCodes. 
		this.name = name;
		this.notes = notes;
		ID = (name.hashCode()+notes.hashCode())%10000;
	}
	
	public int getId(){
		return ID;
	}
	
	public String getName(){
		return name;
	}
	
	public String getNotes(){
		return notes;
	}
	
	public void addNotes(String notes){
		this.notes = notes;
	}
}
