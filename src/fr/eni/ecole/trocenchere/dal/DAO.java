package fr.eni.ecole.trocenchere.dal;

import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

public interface DAO {

	public abstract User selectUser(String username) throws BusinessException;
	public abstract void createUser(User data) throws BusinessException;
	public abstract boolean checkUser(String user) throws BusinessException;
	public abstract boolean checkEmail(String email) throws BusinessException;

}
