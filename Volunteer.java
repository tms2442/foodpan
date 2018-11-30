package aurora_food_pantry;

import java.time.LocalDate;
import java.util.Date;

import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class Volunteer {
	//Instance variables
	int id;
	String firstName, lastName;
	String dob;
	String affiliation;
	String retired;
	String phone, email, street, city, state,
	emergencyName, emergencyPhone;
	String zip;
	String startDate;
	String endDate;
	String searchparam;
	
	//Constructor
	public Volunteer(int id, String firstName, String lastName, String dob, String affiliation, String retired, String phone,
			String email, String street, String city, String state, String emergencyName, String emergencyPhone,
			String zip, String startDate, String endDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.affiliation = affiliation;
		this.retired = retired;
		this.phone = phone;
		this.email = email;
		this.street = street;
		this.city = city;
		this.state = state;
		this.emergencyName = emergencyName;
		this.emergencyPhone = emergencyPhone;
		this.zip = zip;
		this.startDate = startDate;
		this.endDate = endDate;
	}


	public Volunteer() {
		// TODO Auto-generated constructor stub
	}

	//Getters and setters
	
	public String getSearchParam() {
		return searchparam;
	}
	
	public void setSearchParam(String object) {
		this.searchparam = object;
	}
	
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

	public String getDob() {
		return dob;
	}

	public void setDob(String stringDOB) {
		this.dob = stringDOB;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String isRetired() {
		return retired;
	}

	public void setRetired(String retired) {
		this.retired = retired;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmergencyName() {
		return emergencyName;
	}

	public void setEmergencyName(String emergencyName) {
		this.emergencyName = emergencyName;
	}

	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String string) {
		this.zip = string;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String stringLocalDate) {
		this.startDate = stringLocalDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String string) {
		this.endDate = string;
	}

	//ToString
	public String toString() {
		return "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", affiliation="
				+ affiliation + ", retired=" + retired + ", phone=" + phone + ", email=" + email + ", street=" + street
				+ ", city=" + city + ", state=" + state + ", emergencyName=" + emergencyName + ", emergencyPhone="
				+ emergencyPhone + ", zip=" + zip + ", startDate=" + startDate + ", endDate=" + endDate;
	}

	
	
	
}
