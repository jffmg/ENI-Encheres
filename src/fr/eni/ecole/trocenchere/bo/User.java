package fr.eni.ecole.trocenchere.bo;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -8342994380206163309L;
	private String user;
	private int idUser;
	private String name;
	private String firstName;
	private String email;
	private String phone;
	private String street;
	private String postCode;
	private String city;
	private String passwordEncrypted;
	private int credit;
	private boolean isAdmin;

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the postcode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param postcode the postcode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the passwordEncrypted
	 */
	public String getPasswordEncrypted() {
		return passwordEncrypted;
	}

	/**
	 * @param passwordEncrypted the passwordEncrypted to set
	 */
	public void setPasswordEncrypted(String passwordEncrypted) {
		this.passwordEncrypted = passwordEncrypted;
	}

	/**
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}

	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public User(String user, String name, String firstName, String email, String phone, String street, String postCode,
			String city, String passwordEncrypted) {
		this.user = user;
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.phone = phone;
		this.street = street;
		this.postCode = postCode;
		this.city = city;
		this.passwordEncrypted = passwordEncrypted;
	}

	public User() {
	}

}
