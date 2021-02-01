package fr.eni.ecole.trocenchere.dal;

import fr.eni.ecole.trocenchere.dal.jdbc.DAOJdbcImpl;

public abstract class DAOFactory {

	private static DAO Dao;

	public static DAO getUserDao() {

		Dao = new DAOJdbcImpl();
		return Dao;
	}
}

