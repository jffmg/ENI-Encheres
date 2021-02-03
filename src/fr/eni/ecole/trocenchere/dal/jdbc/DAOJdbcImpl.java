package fr.eni.ecole.trocenchere.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.gestion.erreurs.CodesResultatDAL;

public class DAOJdbcImpl implements DAO {

	private static final String SQL_SELECT_USER_BY_USER = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE pseudo=?;";
	private static final String SQL_INSERT_USER = "INSERT INTO utilisateurs(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE email=?;";

	private static final String SQL_SELECT_ARTICLE_BY_CATEGORY = "SELECT * FROM articles_vendus WHERE no_categorie = ?";
	private static final String SQL_SELECT_ARTICLE_BY_KEYWORD = "SELECT * FROM articles_vendus WHERE nom_article LIKE ?;";
	private static final String SQL_SELECT_ARTICLE_BY_STATUS = "SELECT * FROM articles_vendus WHERE etat_vente = ?;";

	private static final String SQL_SELECT_ALL_EC_ARTICLES = "SELECT * FROM articles_vendus WHERE etat_vente = 'EC';";
	private static final String SQL_SELECT_EC_ARTICLES_BY_KEYWORD = "SELECT * FROM articles_vendus WHERE etat_vente = 'EC' AND nom_article LIKE %?%;";
	private static final String SQL_SELECT_EC_ARTICLES_BY_CATEGORY = "SELECT * FROM articles_vendus WHERE etat_vente = 'EC' AND no_categorie = ?;";
	private static final String SQL_SELECT_EC_ARTICLES_BY_KEYWORD_AND_CATEGORY = "SELECT * FROM articles_vendus WHERE etat_vente = 'EC' AND no_categorie = ? AND nom_article LIKE %?%;";

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
		ResultSet rs = null;
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
			rs = prepStmt.getGeneratedKeys();
			if (rs.next()) {
				data.setIdUser(rs.getInt(1));
			}

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

	@Override
	public List<Article> selectArticlesEC(String keyWord, int category) throws BusinessException {


		String kw=keyWord;
		int cat=category;

		System.out.println(kw + " " + cat);

		List<Article> listeArticles = new ArrayList<>();
		PreparedStatement pstmt = null;

		try (Connection cnx = ConnectionProvider.getConnection()) {

			if (category == 0) {
				if (keyWord == "" || keyWord == null) {
					pstmt = cnx.prepareStatement(SQL_SELECT_ALL_EC_ARTICLES);
				} else {
					pstmt = cnx.prepareStatement(SQL_SELECT_EC_ARTICLES_BY_KEYWORD);
					pstmt.setString(1, keyWord);
				}
			} else {
				if (keyWord == "" || keyWord == null) {
					pstmt = cnx.prepareStatement(SQL_SELECT_EC_ARTICLES_BY_CATEGORY);
					pstmt.setInt(1, category);
				} else {
					pstmt = cnx.prepareStatement(SQL_SELECT_EC_ARTICLES_BY_KEYWORD_AND_CATEGORY);
					pstmt.setInt(1, category);
					pstmt.setString(2, keyWord);
				}
			}
			ResultSet rs = pstmt.executeQuery();
			Article currentArticle = new Article();

			while (rs.next()) {
				if (rs.getInt("no_article") != currentArticle.getIdArticle()) {
					currentArticle = articleBuilder(rs);
					listeArticles.add(currentArticle);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
			throw businessException;
		}
		System.out.println(listeArticles);
		return listeArticles;
	}

	private Article articleBuilder(ResultSet rs) throws SQLException {
		Article article = new Article(rs.getString("nom_article"), rs.getString("description"),
				rs.getDate("date_debut_enchere").toLocalDate(), rs.getDate("date_fin_enchere").toLocalDate(),
				rs.getInt("prix_initial"), rs.getString("etat_vente"), rs.getInt("no_categorie"),
				rs.getInt("no_utilisateur"));
		return article;
	}

}
