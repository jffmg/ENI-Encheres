package fr.eni.ecole.trocenchere.dal;

import fr.eni.ecole.trocenchere.bo.User;

public interface DAO {

	public abstract User selectUser(String username);

}
