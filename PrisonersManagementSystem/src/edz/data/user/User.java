package edz.data.user;

import java.time.LocalDate;

public class User {
	
	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private String password;
	private String phone;
	private String email;
	private String date; //should be YYYY-MM-DD
	
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}
	
	public String getPassword(){
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getDate() {
		return date;
	}
	
	public static class Builder{
		
		private int id;
		private String firstName;
		private String lastName;
		private int age;
		private String password;
		private String phone;
		private String email;
		private String date;
		
		public Builder(String firstName, String lastName){
			this.firstName = firstName;
			this.lastName = lastName;
		}
		
		
		public Builder id(int val){
			id = val;
			return this;
		}
		
		public Builder age(int val){
			age = val;
			return this;
		}
		
		public Builder password(String val){
			password = val;
			return this;
		}
		
		public Builder phone(String val){
			phone = val;
			return this;
		}
		
		public Builder email(String val){
			email = val;
			return this;
		}
		
		public Builder date(String val){
			date = val;
			return this;
		}
		
		public User build(){
			return new User(this);
		}
		
	}
	
	private User(Builder builder){
		id = builder.id;
		firstName = builder.firstName;
		lastName = builder.lastName;
		age = builder.age;
		password = builder.password;
		phone = builder.phone;
		email = builder.email;
		date = builder.date;
	}
	
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", phone=" + phone
				+ ", email=" + email + ", date=" + date + "]";
	}
}
