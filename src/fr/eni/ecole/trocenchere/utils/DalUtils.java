package fr.eni.ecole.trocenchere.utils;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.Category;
import fr.eni.ecole.trocenchere.bo.PickUp;
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
	
	public static PreparedStatement prepareStatement1Param(User data, PreparedStatement pstmt, Object p1) throws SQLException {
		pstmt.setObject(1, p1);
		return pstmt;
	}
	
	public static PreparedStatement prepareStatement2Params(User data, PreparedStatement pstmt, Object p1, Object p2) throws SQLException {
		pstmt.setObject(1, p1);
		pstmt.setObject(2, p2);
		return pstmt;
	}
	
	public static PreparedStatement prepareStatement3Params(User data, PreparedStatement pstmt, Object p1, Object p2, Object p3) throws SQLException {
		pstmt.setObject(1, p1);
		pstmt.setObject(2, p2);
		pstmt.setObject(3, p3);
		return pstmt;
	}
	
	public static PreparedStatement prepareStatement4Params(User data, PreparedStatement pstmt, Object p1, Object p2, Object p3, Object p4) throws SQLException {
		pstmt.setObject(1, p1);
		pstmt.setObject(2, p2);
		pstmt.setObject(3, p3);
		pstmt.setObject(4, p4);
		return pstmt;
	}
	
	public static PreparedStatement prepareStatementSellArticle(PreparedStatement pstmt, Article article, int userId) throws SQLException {
		pstmt.setString(1, article.getName());
		pstmt.setString(2, article.getDescription());
		pstmt.setTimestamp(3, Timestamp.valueOf(article.getBidStartDate()));
		pstmt.setObject(4, Timestamp.valueOf(article.getBidEndDate()));
		pstmt.setInt(5, article.getStartingBid());
		pstmt.setInt(6, userId);
		pstmt.setInt(7, article.getIdCategory());
		pstmt.setString(8, article.getStatus());
		return pstmt;
	}
	
	public static PreparedStatement prepareStatementUpdateArticle(PreparedStatement pstmt, Article article) throws SQLException {
		pstmt.setString(1, article.getName());
		pstmt.setString(2, article.getDescription());
		pstmt.setTimestamp(3, Timestamp.valueOf(article.getBidStartDate()));
		pstmt.setObject(4, Timestamp.valueOf(article.getBidEndDate()));
		pstmt.setInt(5, article.getStartingBid());
		pstmt.setInt(6, article.getIdCategory());
		pstmt.setString(7, article.getStatus());
		pstmt.setInt(8, article.getIdArticle());
		return pstmt;
	}
	
	public static PreparedStatement prepareStatementpickUp(PreparedStatement pstmt, PickUp pickUp, int articleId) throws SQLException {
		pstmt.setInt(1, articleId);
		pstmt.setString(2, pickUp.getStreet());
		pstmt.setString(3, pickUp.getPostCode());
		pstmt.setString(4, pickUp.getCity());
		return pstmt;
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
	
	//basic Display (non connected & connedted w/o filters)
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
		Category category = categoryBuilder(rs);
		Article article = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"),
				rs.getTimestamp("date_debut_enchere").toLocalDateTime(), rs.getTimestamp("date_fin_enchere").toLocalDateTime(),
				rs.getInt("prix_initial"), rs.getInt("prix_vente"), rs.getString("etat_vente"), rs.getInt("no_categorie"), user, rs.getInt("no_utilisateur"), category);
		
		System.out.println(rs.getString("pseudo"));
		
		return article;
	}

	private static Category categoryBuilder(ResultSet rs) throws SQLException {
		Category cat = new Category();
		cat.setCategoryId(rs.getInt("no_categorie"));
		cat.setCategoryLabel(rs.getString("libelle"));
		return cat;
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
	
	//method for getting category int from category String
		public static int categoryStringToInteger(String category) {
			Integer categoryInt = 0;
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
		
	public static String categoryIntToString(int categoryInt) {
		String category = "Toutes";

		switch (categoryInt) {
		case 1:
			category = "Informatique";
			break;
		case 2:
			category = "Ameublement";
			break;
		case 3:
			category = "Vêtement";
			break;
		case 4:
			category = "Sport & Loisirs";
			break;
		}
		
		return category;
	}

	public static String dateFormatterDateToString(LocalDateTime date) {
		String dateString = null;
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		formatter = DateTimeFormatter.ofPattern("dd/MM/yyy à HH:mm");
		dateString = date.format(formatter);
		System.out.println(dateString);
		
		return dateString;
	}


}
