package fr.eni.ecole.trocenchere.dal;

import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

public interface DAO {

	public abstract User selectUser(String username) throws BusinessException;

}
