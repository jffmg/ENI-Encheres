package fr.eni.ecole.trocenchere.bo;

public class PickUp {
	
	String street;
	String city;
	String postCode;
	
	public PickUp() {
	}
	
	public PickUp(String street, String postCode, String city) {
		this.street=street;
		this.postCode=postCode;
		this.city=city;
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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}
