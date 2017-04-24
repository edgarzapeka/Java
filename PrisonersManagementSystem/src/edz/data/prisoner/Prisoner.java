/**
 * 
 */
package edz.data.prisoner;

import java.time.LocalDate;

/**
 * @author edz
 *
 */
public class Prisoner {

	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private String race;
	private String sex;
	private String releaseDate;
	private String confinmentDate;
	private int location;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getConfinmentDate() {
		return confinmentDate;
	}
	public void setConfinmentDate(String confinmentDate) {
		this.confinmentDate = confinmentDate;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "Prisoner [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", registrationNumber=" + ", race=" + race + ", sex=" + sex + ", releaseDate="
				+ releaseDate + ", confinmentDate=" + confinmentDate + ", location=" + location + "]";
	}
	
	public static class Builder{
		
		private int id;
		private String firstName;
		private String lastName;
		private int age;
		private String race;
		private String sex;
		private String releaseDate;
		private String confinmentDate;
		private int location;
		
		public Builder(int id){
			this.id = id;
		}
		
		public Builder firstName(String val){
			firstName = val;
			return this;
		}
		
		public Builder lastName(String val){
			lastName = val;
			return this;
		}
		
		public Builder age(int val){
			age = val;
			return this;
		}
		
		public Builder race(String val){
			race = val;
			return this;
		}
		
		public Builder sex(String val){
			sex = val;
			return this;
		}
		
		public Builder releaseDate(String val){
			releaseDate = val;
			return this;
		}
		
		public Builder confinmentDate(String val){
			confinmentDate = val;
			return this;
		}
		
		public Builder location(int val){
			location = val;
			return this;
		}
		
		public Prisoner build(){
			return new Prisoner(this);
		}
		
	}
	
	private Prisoner(Builder builder){
		id = builder.id;
		firstName = builder.firstName;
		lastName = builder.lastName;
		age = builder.age;
		race = builder.race;
		sex = builder.sex;
		releaseDate = builder.releaseDate;
		confinmentDate = builder.confinmentDate;
		location = builder.location;
	}
	
}
