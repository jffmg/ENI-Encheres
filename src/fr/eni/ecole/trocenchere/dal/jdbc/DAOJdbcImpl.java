package fr.eni.ecole.trocenchere.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocencheres.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocencheres.gestion.erreurs.CodesResultatDAL;

public class DAOJdbcImpl implements DAO{

	private static final String SQL_SELECT_USER_BY_USER = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE pseudo=?";

	@Override
	public User selectUser(String userName) {
		User user = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_SELECT_USER_BY_USER);
			prepStmt.setString(1, userName);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				user = buildUser(rs);
			}
		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
			e.printStackTrace();
		}
		return user;
	}

	public User buildUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUser(rs.getString("pseudo"));
		user.setPasswordEncrypted(rs.getString("mot_de_passe"));
		user.setName(rs.getString("nom"));
		user.setFirstName(rs.getString("prenom"));
		user.setEmail(rs.getString("email"));
		user.setPhone(rs.getString("telephone"));
		user.setStreet(rs.getString("rue"));
		user.setCity(rs.getString("ville"));
		user.setPostCode(rs.getString("code_postal"));
		user.setCredit(rs.getInt("credit"));
		user.setAdmin(rs.getBoolean("administrateur"));
		return user;

	}

	@Override
	public void createUser(User data) {
		// TODO Auto-generated method stub
		
	}

}
