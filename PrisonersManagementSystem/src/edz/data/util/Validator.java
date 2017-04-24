package edz.data.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	//Name with one whitespace allowed
	public static Boolean name(String val){
		Pattern p = Pattern.compile("^[a-zA-Z]+[\\s]*[a-zA-Z]*$");
		Matcher m = p.matcher(val);
		return m.find();
	}
	
	//Maximum 2 digit numbers
	public static Boolean age(String val){
		Pattern p = Pattern.compile("^\\d{1,2}$");
		Matcher m = p.matcher(val);
		return m.find();
	}
	
	//10 digit number, only numbers
	public static Boolean phone(String val){
		Pattern p = Pattern.compile("^\\d{10}$");
		Matcher m = p.matcher(val);
		return m.find();
	}
	
	//Stolen from -> https://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
	public static Boolean email(String val){
		Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher m = p.matcher(val);
		return m.find();
	}
	
	//YYYY-MM-DD
	public static Boolean date(String val){
		Pattern p = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
		Matcher m = p.matcher(val);
		return m.find();
	}
	
	// Minimum 8 characters at least 1 Alphabet and 1 Number
	public static Boolean password(String val){
		Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
		Matcher m = p.matcher(val);
		return m.find();
	}
}
