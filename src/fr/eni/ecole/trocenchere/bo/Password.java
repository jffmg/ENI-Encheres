package fr.eni.ecole.trocenchere.bo;

public class Password {
	
	private String password;
	
	//function to encrypt password
	public String encrypt(String password) {
		String passwordEncrypted="";
		for (int i=0; i<password.length();i++)  {
			int c=password.charAt(i)^48;
			passwordEncrypted=passwordEncrypted+(char)c;
		}

		return passwordEncrypted;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
