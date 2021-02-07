package fr.eni.ecole.trocenchere.utils;

public class TestPasswordEncrypted {
	
	public static void main(String[] args) {
		String passwordtest = "test";
		String passwordEncypted = encrypt(passwordtest);
		System.out.println("Mot de passse test : "+ passwordtest);
		System.out.println("Mot de passe crypt� : " + passwordEncypted) ;
	}
	

	//function to encrypt password
	private static String encrypt(String password) {
		String passwordEncrypted="";
		for (int i=0; i<password.length();i++)  {
			int c=password.charAt(i)^48;
			passwordEncrypted=passwordEncrypted+(char)c;
		}

		return passwordEncrypted;
	}

}
