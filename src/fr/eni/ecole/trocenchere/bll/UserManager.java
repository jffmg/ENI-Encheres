package fr.eni.ecole.trocenchere.bll;

import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.dal.DAOFactory;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

public class UserManager {
	private DAO userDao;

	// TODO
	public UserManager() {
		this.userDao = DAOFactory.getUser();
	}

	// method in order to get user data from pseudo
	public User selectUser(String userName) throws BusinessException {
		return this.userDao.selectUser(userName);
	}

}
