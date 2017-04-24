package edz.data.crime;

public class Crime {

	private int id;
	private String title;
	private String description;
	private String date;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	private int prisonerId;
	
	public Crime(){
		
	}
	
	public Crime(int id, String title, String description, String date, int prisonerId){
		this.id = id;
		this.title = title;
		this.description = description;
		this.prisonerId = prisonerId;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrisonerId() {
		return prisonerId;
	}
	public void setPrisonerId(int prisonerId) {
		this.prisonerId = prisonerId;
	}
	
	
}
