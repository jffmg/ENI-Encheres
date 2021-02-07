package fr.eni.ecole.trocenchere.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;

import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.User;

public class DalUtils {
	
	
	/*
	 * REQUEST STATEMENT FUNCTION
	 */
	public static void prepareStatementPersonalInfo(User data, PreparedStatement prepStmt) throws SQLException {
		prepStmt.setString(1, data.getUser());
		prepStmt.setString(2, data.getName());
		prepStmt.setString(3, data.getFirstName());
		prepStmt.setString(4, data.getEmail());
		prepStmt.setString(5, data.getPhone());
		prepStmt.setString(6, data.getStreet());
		prepStmt.setString(7, data.getPostCode());
		prepStmt.setString(8, data.getCity());
		prepStmt.setString(9, data.getPasswordEncrypted());
	}
	
	
	/**
	 * USER : SUPPORT METHODS
	 */
	
	public static User buildUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setIdUser(rs.getInt("no_utilisateur"));
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
	
	/**
	 * ARTICLES : SUPPORT METHODS
	 */
	
	public static PreparedStatement basicDisplay(String keyword, int category, PreparedStatement pstmt, Connection cnx) throws SQLException {
		
		if (category == 0) {
			if (keyword == null || keyword == "") {
				pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_ALL_EC_ARTICLES);
			} else {
				pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_EC_ARTICLES_BY_KEYWORD);
				pstmt.setString(1, keyword);
			}
		} else {
			if (keyword == "" || keyword == null) {
				pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_EC_ARTICLES_BY_CATEGORY);
				pstmt.setInt(1, category);
			} else {
				pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_DISABLE_USER);
				pstmt.setInt(1, category);
				pstmt.setString(2, keyword);
			}
		}
		
		return pstmt;
	}
	
	//article builder
	public static Article articleBuilder(ResultSet rs) throws SQLException {
		User user = buildUser(rs);
		Article article = new Article(rs.getString("nom_article"), rs.getString("description"),
				rs.getDate("date_debut_enchere").toLocalDate(), rs.getDate("date_fin_enchere").toLocalDate(),
				rs.getInt("prix_initial"), rs.getString("etat_vente"), rs.getInt("no_categorie"), user, rs.getInt("no_utilisateur"));
		
		System.out.println(rs.getString("pseudo"));
		
		return article;
	}

	// method for getting rid of frenchy accents
	private static String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[^\\p{ASCII}]", "");
		return s;
	}
	
	//method for getting category int from category String
	public static int categoryStringToInt(String category) {
		int categoryInt = 0;
		String catStrimAccents = stripAccents(category);

		// changing labels to int
		switch (catStrimAccents.toLowerCase().trim()) {
		case "informatique":
			categoryInt = 1;
			break;
		case "ameublement":
			categoryInt = 2;
			break;
		case "vatement":
			categoryInt = 3;
			break;
		case "sport & loisirs":
			categoryInt = 4;
			break;
		// default = all selected
		default:
			categoryInt = 0;
			break;
		}
		
		return categoryInt;
		
		
	}

	


}
