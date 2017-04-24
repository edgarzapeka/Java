package edz.data.location;

public class Location {

	private int id;
	private String name;
	private String description;
	private String address;
	
	public Location(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public static class Builder{
		private int id;
		private String name;
		private String description;
		private String address;
		
		public Builder(int id){
			this.id = id;
		}
		
		public Builder name(String val){
			name = val;
			return this;
		}
		
		public Builder description(String val){
			description = val;
			return this;
		}
		
		public Builder address(String val){
			address = val;
			return this;
		}
		
		public Location build(){
			return new Location(this);
		}
	}
	
	private Location(Builder builder){
		id = builder.id;
		name = builder.name;
		description = builder.description;
		address = builder.address;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s", name, address);
	}
	
	
}
