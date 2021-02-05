package fr.eni.ecole.trocenchere.utils;

public class PasswordUtils {
	
	//function to encrypt password
	public static String encrypt(String password) {
		String passwordEncrypted="";
		for (int i=0; i<password.length();i++)  {
			int c=password.charAt(i)^48;
			passwordEncrypted=passwordEncrypted+(char)c;
		}

		return passwordEncrypted;
	}

}
