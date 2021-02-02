package fr.eni.ecole.trocenchere.bll;

import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.dal.DAOFactory;
import fr.eni.ecole.trocencheres.gestion.erreurs.BusinessException;

public class UserManager {
	private DAO userDao;

	// TODO
	public UserManager() {
		this.userDao = DAOFactory.getUser();
	}

	// method in order to get user data from pseudo
	public User selectUser(String userName) {
		return this.userDao.selectUser(userName);
	}

	// method in order to create a new user in database
	public User addUser(String user, String name, String firstName, String email, String phone, String street,
			String postCode, String city, String passwordEncrypted) throws BusinessException {
		
		BusinessException be = new BusinessException();
		
		User currentUser = new User(user, name, firstName, email, phone, street, postCode, city, passwordEncrypted);
		this.validateUser(currentUser, be);
		
		if(!be.hasErreurs())
		{
			this.userDao.createUser(currentUser);
		}
		
		if(be.hasErreurs())
		{
			throw be;
		}
	
		return currentUser;

	}
	
	//User validation before creating in database
	private void validateUser(User currentUser, BusinessException be) {
		
	}

}
