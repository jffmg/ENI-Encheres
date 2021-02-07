package fr.eni.ecole.trocenchere.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.trocenchere.bll.ArticleManager;
import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.gestion.erreurs.CodesResultatDAL;
import fr.eni.ecole.trocenchere.utils.DalUtils;
import fr.eni.ecole.trocenchere.utils.SQL_REQUESTS_Utils;

public class DAOJdbcImpl implements DAO {

	/**
	 *  USER : MAIN METHODS (Create - Select - Update - Delete)
	 */
	
	@Override
	public User selectUser(String userName) throws BusinessException {
		User user = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BY_USER);
			prepStmt.setString(1, userName);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				user = DalUtils.buildUser(rs);
			}
		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
			e.printStackTrace();
			throw businessException;
		}
		return user;
	}
	
	@Override
	public void createUser(User data) throws BusinessException {
		PreparedStatement prepStmt = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_INSERT_USER);
			DalUtils.prepareStatementPersonalInfo(data, prepStmt);
			prepStmt.setInt(10, 100);
			prepStmt.setInt(11, 0);
			prepStmt.executeUpdate();

		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			e.printStackTrace();
			throw businessException;
		}

	}
	
	@Override
	public void disableUser(String userName) throws BusinessException {
		// Test User has articles in sales
		boolean hasArticlesToSale = checkArticlesToSale(userName);

		// User has articles to sales => error message
		if(hasArticlesToSale) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.USER_CANNOT_BE_DISABLED_ARTICLES_TO_SALES);
			businessException.printStackTrace();
			throw businessException;
		}

		// test OK => disable User
		else{
			PreparedStatement prepStmt = null;
			try (Connection cnx = ConnectionProvider.getConnection()) {
				prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_DISABLE_USER);
				prepStmt.setString(1, userName);
				prepStmt.executeUpdate();
			} catch (SQLException e) {
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.DISABLE_USER_ECHEC);
				e.printStackTrace();
				throw businessException;
			}
		}
	}
	

	@Override
	public boolean checkArticlesToSale(String userName) throws BusinessException {
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		boolean hasArticlesToSale = false;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES);
			prepStmt.setString(1, userName);
			prepStmt.setString(2, "EC");
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				hasArticlesToSale = true;
			}
			return hasArticlesToSale;

		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
			e.printStackTrace();
			throw businessException;
		}

	}
	
	@Override
	public void updateUser(User data) throws BusinessException {
		PreparedStatement prepStmt = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_UPDATE_USER);
			DalUtils.prepareStatementPersonalInfo(data, prepStmt);
			prepStmt.setInt(10, data.getIdUser());
			prepStmt.executeUpdate();

		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJECT_ECHEC);
			e.printStackTrace();
			throw businessException;
		}
	}
	

	/**
	 * USER : CHECKERS
	 */
	
	@Override
	public boolean checkUser(String user) throws BusinessException {
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		boolean userExists = false;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BY_USER);
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
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BY_EMAIL);
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
		

	/**
	 * ARTICLES : MAIN METHODS (Select, Create, Update, Delete)
	 */
	
	// method to display articles with filters (Non Connected)
	@Override
	public List<Article> displayArticles(String keyword, String category, HttpServletRequest request) throws BusinessException {

		List<Article> articlesSelected = new ArrayList<>();
		int categorySelected = DalUtils.categoryStringToInt(category);

		PreparedStatement pstmt = null;

		try (Connection cnx = ConnectionProvider.getConnection()) {

			pstmt = DalUtils.basicDisplay(keyword, categorySelected, pstmt, cnx);
			
			ResultSet rs = pstmt.executeQuery();
			Article currentArticle = new Article();

			while (rs.next()) {
				if (rs.getInt("no_article") != currentArticle.getIdArticle()) {
					currentArticle = DalUtils.articleBuilder(rs);
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
	
	
	
	// method to display articles with filters (Non Connected)
		@Override
		public List<Article> displayArticlesConnected(String userName, String keyword, String category, String buyOrSell, String checkBox, HttpServletRequest request) throws BusinessException {

			List<Article> articlesSelected = new ArrayList<>();
			int categorySelected = DalUtils.categoryStringToInt(category);

			PreparedStatement pstmt = null;
			int userID = 0;
			User user = selectUser(userName);
			userID = user.getIdUser();
			

			try (Connection cnx = ConnectionProvider.getConnection()) {
				
				if (buyOrSell.equalsIgnoreCase("buy1")) {
					switch (checkBox) {
					case "myBids" : 
						if (categorySelected == 0) {
							if (keyword == null || keyword == "") {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BIDS_ARTICLES);
								pstmt.setInt(1, userID);
							} else {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BIDS_ARTICLES_BY_KEYWORD);
								DalUtils.prepareStatementIntString(user, pstmt, userID, keyword);
							}
						} else {
							if (keyword == "" || keyword == null) {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BIDS_ARTICLES_BY_CATEGORY);
								DalUtils.prepareStatement2Int(user, pstmt, userID, categorySelected);
							} else {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BIDS_ARTICLES_BY_KEYWORD_AND_CATEGORY);
								DalUtils.prepareStatement2IntString(user, pstmt, userID, categorySelected,  keyword);
							}
						}
						;break;
					case "myWonBids" : 
						if (categorySelected == 0) {
							if (keyword == null || keyword == "") {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_WON_BIDS_ARTICLES);
								pstmt.setInt(1, userID);
							} else {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_WON_BIDS_ARTICLES_BY_KEYWORD);
							}
						} else {
							if (keyword == "" || keyword == null) {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_WON_BIDS_ARTICLES_BY_CATEGORY);
								DalUtils.prepareStatement2Int(user, pstmt, userID, categorySelected);
							} else {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_WON_BIDS_ARTICLES_BY_KEYWORD_AND_CATEGORY);
								DalUtils.prepareStatement2IntString(user, pstmt, userID, categorySelected,  keyword);
							}
						}
						;break;
					default : 
						pstmt = DalUtils.basicDisplay(keyword, categorySelected, pstmt, cnx);
						; break;
					}
					
				} else {
					switch (checkBox) {
					case "notStartedSales" : 
						if (categorySelected == 0) {
							if (keyword == null || keyword == "") {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
							} else {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
								pstmt.setString(3, keyword);
							}
						} else {
							if (keyword == "" || keyword == null) {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setString(2, "CR");
								pstmt.setInt(3, categorySelected);
							} else {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY);
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
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES);
								pstmt.setString(1, userName);
								pstmt.setString(2, "VD");
							} else {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD);
								pstmt.setString(1, userName);
								pstmt.setString(2, "VD");
								pstmt.setString(3, keyword);
							}
						} else {
							if (keyword == "" || keyword == null) {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setString(2, "VD");
								pstmt.setInt(3, categorySelected);
							} else {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY);
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
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES);
								pstmt.setString(1, userName);
								pstmt.setString(2, "EC");
							} else {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD);
								pstmt.setString(1, userName);
								pstmt.setString(2, "EC");
								pstmt.setString(3, keyword);
							}
						} else {
							if (keyword == "" || keyword == null) {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setString(2, "EC");
								pstmt.setInt(3, categorySelected);
							} else {
								pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY);
								pstmt.setString(1, userName);
								pstmt.setString(2, "EC");
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
						currentArticle = DalUtils.articleBuilder(rs);
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
