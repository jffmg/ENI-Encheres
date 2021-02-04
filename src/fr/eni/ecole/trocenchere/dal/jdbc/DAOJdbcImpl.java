package fr.eni.ecole.trocenchere.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.eni.ecole.trocenchere.bll.ArticleManager;
import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.gestion.erreurs.CodesResultatDAL;

public class DAOJdbcImpl implements DAO {

	private static final String SQL_SELECT_USER_BY_USER = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE pseudo=?;";
	private static final String SQL_INSERT_USER = "INSERT INTO utilisateurs(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE email=?;";

	private static final String SQL_SELECT_ALL_EC_ARTICLES = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE a.etat_vente = 'EC'";
	
	private static final String SQL_SELECT_EC_ARTICLES_BY_KEYWORD = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE a.etat_vente = 'EC' AND nom_article LIKE CONCAT( '%',?,'%');";
	
	private static final String SQL_SELECT_EC_ARTICLES_BY_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE a.etat_vente = 'EC' AND no_categorie = ?;";
	
	private static final String SQL_SELECT_EC_ARTICLES_BY_KEYWORD_AND_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE a.etat_vente = 'EC' AND no_categorie = ? AND nom_article LIKE CONCAT( '%',?,'%');";
	
	private static final String SQL_SELECT_USER_BIDS_ARTICLES = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "WHERE a.etat_vente = 'EC' AND e.no_utilisateur = ?;";
	
	private static final String SQL_SELECT_USER_BIDS_ARTICLES_BY_KEYWORD = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "WHERE a.etat_vente = 'EC' AND e.no_utilisateur = ? AND a.nom_article LIKE CONCAT( '%',?,'%');";
	
	private static final String SQL_SELECT_USER_BIDS_ARTICLES_BY_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "WHERE a.etat_vente = 'EC' AND e.no_utilisateur = ? AND a.no_categorie = ?;";
	
	private static final String SQL_SELECT_USER_BIDS_ARTICLES_BY_KEYWORD_AND_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "WHERE a.etat_vente = 'EC' AND e.no_utilisateur = ? AND a.no_categorie = ? AND a.nom_article LIKE CONCAT( '%',?,'%');";
	
	private static final String SQL_SELECT_WON_BIDS_ARTICLES = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "WHERE (a.etat_vente = 'VD' OR a.etat_vente = 'RT') AND e.no_utilisateur = ?;";
	
	private static final String SQL_SELECT_WON_BIDS_ARTICLES_BY_KEYWORD = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "WHERE (a.etat_vente = 'VD' OR a.etat_vente = 'RT') AND e.no_utilisateur = ? AND a.nom_article LIKE CONCAT( '%',?,'%');";
	
	private static final String SQL_SELECT_WON_BIDS_ARTICLES_BY_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "WHERE (a.etat_vente = 'VD' OR a.etat_vente = 'RT') AND e.no_utilisateur = ? AND a.no_categorie = ?;";
	
	private static final String SQL_SELECT_WON_BIDS_ARTICLES_BY_KEYWORD_AND_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "WHERE (a.etat_vente = 'VD' OR a.etat_vente = 'RT') AND e.no_utilisateur = ? AND a.no_categorie = ? AND a.nom_article LIKE CONCAT( '%',?,'%');";

	private static final String SQL_SELECT_USER_SALES = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE u.pseudo = ? AND a.etat_vente = ?";
	
	private static final String SQL_SELECT_USER_SALES_BY_KEYWORD = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE u.pseudo = ? AND a.etat_vente = ? AND a.nom_article LIKE CONCAT( '%',?,'%')";
	
	private static final String SQL_SELECT_USER_SALES_BY_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE u.pseudo = ? AND a.etat_vente = ? AND no_categorie = ?";
	
	private static final String SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE u.pseudo = ? AND a.etat_vente = ? AND no_categorie = ? AND a.nom_article LIKE CONCAT( '%',?,'%')";


	@Override
	public User selectUser(String userName) throws BusinessException {
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
			throw businessException;
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
		PreparedStatement prepStmt = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_INSERT_USER);
			prepStmt.setString(1, data.getUser());
			prepStmt.setString(2, data.getName());
			prepStmt.setString(3, data.getFirstName());
			prepStmt.setString(4, data.getEmail());
			prepStmt.setString(5, data.getPhone());
			prepStmt.setString(6, data.getStreet());
			prepStmt.setString(7, data.getPostCode());
			prepStmt.setString(8, data.getCity());
			prepStmt.setString(9, data.getPasswordEncrypted());
			prepStmt.setInt(10, 100);
			prepStmt.setInt(11, 0);
			prepStmt.executeUpdate();
			/*
			 * ResultSet rs = prepStmt.getGeneratedKeys(); if (rs.next()) {
			 * data.setIdUser(rs.getInt(1));
			 * 
			 * }
			 */

		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			e.printStackTrace();
		}

	}

	@Override
	public boolean checkUser(String user) throws BusinessException {
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		boolean userExists = false;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_SELECT_USER_BY_USER);
			prepStmt.setString(1, user);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				userExists = true;
			}

			return userExists;

		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
			e.printStackTrace();
			throw businessException;
		}

	}

	@Override
	public boolean checkEmail(String email) throws BusinessException {
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		boolean emailExists = false;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
			prepStmt.setString(1, email);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				emailExists = true;
			}

			return emailExists;

		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
			e.printStackTrace();
			throw businessException;
		}
	}

	private Article articleBuilder(ResultSet rs) throws SQLException {
		User user = new User(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"), rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
		Article article = new Article(rs.getString("nom_article"), rs.getString("description"),
				rs.getDate("date_debut_enchere").toLocalDate(), rs.getDate("date_fin_enchere").toLocalDate(),
				rs.getInt("prix_initial"), rs.getString("etat_vente"), rs.getInt("no_categorie"), user, rs.getInt("no_utilisateur"));
		
		System.out.println(rs.getString("pseudo"));
		
		
		
		return article;
	}

	// method to display articles with filters (Non Connected)
	@Override
	public List<Article> displayArticles(String keyword, String category, HttpServletRequest request) throws BusinessException {

		List<Article> articlesSelected = new ArrayList<>();
		int categorySelected = 0;
		String catStrimAccents = stripAccents(category);

		// changing labels to int
		switch (catStrimAccents.toLowerCase().trim()) {
		case "informatique":
			categorySelected = 1;
			break;
		case "ameublement":
			categorySelected = 2;
			break;
		case "vatement":
			categorySelected = 3;
			break;
		case "sport & loisirs":
			categorySelected = 4;
			break;
		// default = all selected
		default:
			categorySelected = 0;
			break;
		}

		PreparedStatement pstmt = null;

		try (Connection cnx = ConnectionProvider.getConnection()) {

			if (categorySelected == 0) {
				if (keyword == null || keyword == "") {
					pstmt = cnx.prepareStatement(SQL_SELECT_ALL_EC_ARTICLES);
				} else {
					pstmt = cnx.prepareStatement(SQL_SELECT_EC_ARTICLES_BY_KEYWORD);
					pstmt.setString(1, keyword);
				}
			} else {
				if (keyword == "" || keyword == null) {
					pstmt = cnx.prepareStatement(SQL_SELECT_EC_ARTICLES_BY_CATEGORY);
					pstmt.setInt(1, categorySelected);
				} else {
					pstmt = cnx.prepareStatement(SQL_SELECT_EC_ARTICLES_BY_KEYWORD_AND_CATEGORY);
					pstmt.setInt(1, categorySelected);
					pstmt.setString(2, keyword);
				}
			}
			ResultSet rs = pstmt.executeQuery();
			Article currentArticle = new Article();

			while (rs.next()) {
				if (rs.getInt("no_article") != currentArticle.getIdArticle()) {
					currentArticle = articleBuilder(rs);
					articlesSelected.add(currentArticle);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
			throw businessException;
		}
		System.out.println(articlesSelected);

		return articlesSelected;
	}

	// method for getting rid of frenchy accents
	private String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[^\\p{ASCII}]", "");
		return s;
	}
	
	// method to display articles with filters (Non Connected)
		@Override
		public List<Article> displayArticlesConnected(String userName, String keyword, String category, String buyOrSell, String checkBox, HttpServletRequest request) throws BusinessException {

			List<Article> articlesSelected = new ArrayList<>();
			int categorySelected = 0;
			String catStrimAccents = stripAccents(category);

			// changing labels to int
			switch (catStrimAccents.toLowerCase().trim()) {
			case "informatique":
				categorySelected = 1;
				break;
			case "ameublement":
				categorySelected = 2;
				break;
			case "vatement":
				categorySelected = 3;
				break;
			case "sport & loisirs":
				categorySelected = 4;
				break;
			// default = all selected
			default:
				categorySelected = 0;
				break;
			}

			PreparedStatement pstmt = null;

			try (Connection cnx = ConnectionProvider.getConnection()) {
				
				if (buyOrSell=="buy") {
					switch (checkBox) {
					case "myBids" : 
						if (categorySelected == 0) {
							if (keyword == null || keyword == "") {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_BIDS_ARTICLES);
								pstmt.setString(1, userName);
							} else {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_BIDS_ARTICLES_BY_KEYWORD);
								pstmt.setString(1, userName);
								pstmt.setString(2, keyword);
							}
						} else {
							if (keyword == "" || keyword == null) {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_BIDS_ARTICLES_BY_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setInt(2, categorySelected);
							} else {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_BIDS_ARTICLES_BY_KEYWORD_AND_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setInt(2, categorySelected);
								pstmt.setString(3, keyword);
							}
						}
						;break;
					case "myWonBids" : 
						if (categorySelected == 0) {
							if (keyword == null || keyword == "") {
								pstmt = cnx.prepareStatement(SQL_SELECT_WON_BIDS_ARTICLES);
								pstmt.setString(1, userName);
							} else {
								pstmt = cnx.prepareStatement(SQL_SELECT_WON_BIDS_ARTICLES_BY_KEYWORD);
								pstmt.setString(1, userName);
								pstmt.setString(2, keyword);
							}
						} else {
							if (keyword == "" || keyword == null) {
								pstmt = cnx.prepareStatement(SQL_SELECT_WON_BIDS_ARTICLES_BY_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setInt(2, categorySelected);
							} else {
								pstmt = cnx.prepareStatement(SQL_SELECT_WON_BIDS_ARTICLES_BY_KEYWORD_AND_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setInt(2, categorySelected);
								pstmt.setString(3, keyword);
							}
						}
						;break;
					default : 
						articlesSelected = displayArticles(keyword, category, request);
						; break;
					}
					
				} else {
					switch (checkBox) {
					case "notStartedSales" : 
						if (categorySelected == 0) {
							if (keyword == null || keyword == "") {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
							} else {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES_BY_KEYWORD);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
								pstmt.setString(3, keyword);
							}
						} else {
							if (keyword == "" || keyword == null) {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES_BY_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
								pstmt.setInt(3, categorySelected);
							} else {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
								pstmt.setInt(3, categorySelected);
								pstmt.setString(4, keyword);
							}
						}
						;break;
					case "endedSales" : 
						if (categorySelected == 0) {
							if (keyword == null || keyword == "") {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES);
								pstmt.setString(1, userName);
								pstmt.setString(2, "VD");
							} else {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES_BY_KEYWORD);
								pstmt.setString(1, userName);
								pstmt.setString(2, "VD");
								pstmt.setString(3, keyword);
							}
						} else {
							if (keyword == "" || keyword == null) {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES_BY_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setString(2, "VD");
								pstmt.setInt(3, categorySelected);
							} else {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setString(2, "VD");
								pstmt.setInt(3, categorySelected);
								pstmt.setString(4, keyword);
							}
						}
						;break;
					default : 
						if (categorySelected == 0) {
							if (keyword == null || keyword == "") {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
							} else {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES_BY_KEYWORD);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
								pstmt.setString(3, keyword);
							}
						} else {
							if (keyword == "" || keyword == null) {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES_BY_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
								pstmt.setInt(3, categorySelected);
							} else {
								pstmt = cnx.prepareStatement(SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
								pstmt.setInt(3, categorySelected);
								pstmt.setString(4, keyword);
							}
						}
						; break;
					}
				}
				ResultSet rs = pstmt.executeQuery();
				Article currentArticle = new Article();

				while (rs.next()) {
					if (rs.getInt("no_article") != currentArticle.getIdArticle()) {
						currentArticle = articleBuilder(rs);
						articlesSelected.add(currentArticle);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
				throw businessException;
			}
			System.out.println(articlesSelected);

			return articlesSelected;
		}

}
