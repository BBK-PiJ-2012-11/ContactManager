public class ContactImpl implements Contact{
	private int ID;
	private String name;
	private String notes;
	
	ContactImpl(String name,String notes){
		this.name = name;
		this.notes = notes;
		ID = name.hashCode();
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
