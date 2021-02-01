package fr.eni.ecole.trocenchere.bll;

import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.dal.DAOFactory;

public class UserManager {
	private DAO userDao;
	
	// Contructors
	public UserManager() {
		this.userDao = DAOFactory.getUserDao();
	}
	
	// TO DO : method in order tp get user data from pseudo
	public String getPasswordByUser(String user) {
		String password = null;
		return password;
	}

}
