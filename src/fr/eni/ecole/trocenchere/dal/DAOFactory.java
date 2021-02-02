package fr.eni.ecole.trocenchere.dal;

import fr.eni.ecole.trocenchere.dal.jdbc.DAOJdbcImpl;

public abstract class DAOFactory {

	private static DAO dao;

	public static DAO getUser() {

		dao = new DAOJdbcImpl();
		return dao;
	}
}

