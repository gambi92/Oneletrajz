package hu.gambino.data;

import java.sql.Date;

public class CV {
	private Long id;
	private String name;
	private String placeOfBirth;
	private Date dateOfBirth;
	private String nationality;
	private String email;
	private String phone;
	private Date dateOfCreation;
	
	public CV(Long id, String name, String placeOfBirth, Date dateOfBirth, String nationality, String email,
			String phone, Date dateOfCreation) {
		this.id = id;
		this.name = name;
		this.placeOfBirth = placeOfBirth;
		this.dateOfBirth = dateOfBirth;
		this.nationality = nationality;
		this.email = email;
		this.phone = phone;
		this.dateOfCreation = dateOfCreation;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	
	
	
}
