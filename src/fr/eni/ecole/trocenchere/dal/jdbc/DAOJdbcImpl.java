package fr.eni.ecole.trocenchere.dal.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.PickUp;
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


	@Override
	public int selectPoints(int sessionId) throws BusinessException {
		int points = 0;

		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_POINTS_BY_USER);
			prepStmt.setInt(1, sessionId);
			rs = prepStmt.executeQuery();
			if(rs.next())
			{
				points=rs.getInt(1);
			}
		}
		catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
			e.printStackTrace();
			throw businessException;
		}
		return points;
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


	@Override
	public Article selectArticle(String articleID) throws BusinessException {
		Article article = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_ARTICLE_BY_ID);
			prepStmt.setString(1, articleID);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				article = DalUtils.articleBuilder(rs);
			}
		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
			e.printStackTrace();
			throw businessException;
		}
		return article;
	}



	// method to display articles with filters (Non Connected)
	@Override
	public List<Article> displayArticlesConnected(String userName, String keyword, String category, String buyOrSell, String checkBox, HttpServletRequest request) throws BusinessException {

		List<Article> articlesSelected = new ArrayList<>();
		int categorySelected = DalUtils.categoryStringToInteger(category);

		PreparedStatement pstmt = null;
		Integer userID = 0;
		User user = selectUser(userName);
		userID = user.getIdUser();
		String stateArticle = "";

		try (Connection cnx = ConnectionProvider.getConnection()) {

			if (buyOrSell.equalsIgnoreCase("buy1")) {
				switch (checkBox) {
				case "myBids" :
					if (categorySelected == 0) {
						if (keyword == null || keyword == "") {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BIDS_ARTICLES);
							pstmt = DalUtils.prepareStatement1Param(user, pstmt, userID);
						} else {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BIDS_ARTICLES_BY_KEYWORD);
							pstmt = DalUtils.prepareStatement2Params(user, pstmt, userID, keyword);
						}
					} else {
						if (keyword == "" || keyword == null) {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BIDS_ARTICLES_BY_CATEGORY);
							pstmt = DalUtils.prepareStatement2Params(user, pstmt, userID, categorySelected);
						} else {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_BIDS_ARTICLES_BY_KEYWORD_AND_CATEGORY);
							pstmt = DalUtils.prepareStatement3Params(user, pstmt, userID, categorySelected, keyword);
						}
					}break;
				case "myWonBids" :
					if (categorySelected == 0) {
						if (keyword == null || keyword == "") {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_WON_BIDS_ARTICLES);
							pstmt = DalUtils.prepareStatement1Param(user, pstmt, userID);
						} else {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_WON_BIDS_ARTICLES_BY_KEYWORD);
							pstmt = DalUtils.prepareStatement2Params(user, pstmt, userID, keyword);
						}
					} else {
						if (keyword == "" || keyword == null) {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_WON_BIDS_ARTICLES_BY_CATEGORY);
							pstmt = DalUtils.prepareStatement2Params(user, pstmt, userID, categorySelected);
						} else {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_WON_BIDS_ARTICLES_BY_KEYWORD_AND_CATEGORY);
							pstmt = DalUtils.prepareStatement3Params(user, pstmt, userID, categorySelected, keyword);
						}
					}break;
				default :
					pstmt = DalUtils.basicDisplay(keyword, categorySelected, pstmt, cnx);
					; break;
				}

			} else {
				switch (checkBox) {
				case "notStartedSales" :
					stateArticle = "CR";
					if (categorySelected == 0) {
						if (keyword == null || keyword == "") {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES);
							pstmt = DalUtils.prepareStatement2Params(user, pstmt, userName, stateArticle);
						} else {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD);
							pstmt = DalUtils.prepareStatement3Params(user, pstmt, userName, stateArticle, keyword);
						}
					} else {
						if (keyword == "" || keyword == null) {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_CATEGORY);
							pstmt = DalUtils.prepareStatement3Params(user, pstmt, userName, stateArticle, categorySelected);
						} else {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY);
							pstmt = DalUtils.prepareStatement4Params(user, pstmt, userName, stateArticle, categorySelected, keyword);
						}
					}break;
				case "endedSales" :
					stateArticle = "VD";
					if (categorySelected == 0) {
						if (keyword == null || keyword == "") {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES);
							pstmt = DalUtils.prepareStatement2Params(user, pstmt, userName, stateArticle);
						} else {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD);
							pstmt = DalUtils.prepareStatement3Params(user, pstmt, userName, stateArticle, keyword);
						}
					} else {
						if (keyword == "" || keyword == null) {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_CATEGORY);
							pstmt = DalUtils.prepareStatement3Params(user, pstmt, userName, stateArticle, categorySelected);
						} else {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY);
							pstmt = DalUtils.prepareStatement4Params(user, pstmt, userName, stateArticle, categorySelected, keyword);
						}
					}break;
				default :
					stateArticle = "EC";
					if (categorySelected == 0) {
						if (keyword == null || keyword == "") {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES);
							pstmt = DalUtils.prepareStatement2Params(user, pstmt, userName, stateArticle);
						} else {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD);
							pstmt = DalUtils.prepareStatement3Params(user, pstmt, userName, stateArticle, keyword);
						}
					} else {
						if (keyword == "" || keyword == null) {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_CATEGORY);
							pstmt = DalUtils.prepareStatement3Params(user, pstmt, userName, stateArticle, categorySelected);
						} else {
							pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY);
							pstmt = DalUtils.prepareStatement4Params(user, pstmt, userName, stateArticle, categorySelected, keyword);
						}
					} break;
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

	//method to create an article to sell
	@Override
	public void createSellNewArticle(int userId, Article articleToSell, PickUp pickUp) throws BusinessException {
		Article article = createArticleToSell(userId, articleToSell);

		int articleId = article.getIdArticle();

		boolean isPickUpOK = createPickUp(articleId,pickUp);

		if(!isPickUpOK == true) {
			deleteArticleToSell(articleId);
		}

	}

	public Article createArticleToSell(int userId, Article articleToSell) throws BusinessException {
		PreparedStatement prepStmt = null;
		//PreparedStatement st = null;
		ResultSet rs = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
			prepStmt = DalUtils.prepareStatementSellArticle(prepStmt, articleToSell, userId);
			prepStmt.executeUpdate();
			rs = prepStmt.getGeneratedKeys();
			if(rs.next())
			{
				articleToSell.setIdArticle(rs.getInt(1));
			}

		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			e.printStackTrace();
			throw businessException;
		}
		return articleToSell;
	}

	public boolean createPickUp(int articleId, PickUp pickUp) throws BusinessException {
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		boolean test = false;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_INSERT_PICKUP);
			DalUtils.prepareStatementpickUp(prepStmt, pickUp, articleId);
			int i = prepStmt.executeUpdate();

			if (i > 0) {
				test=true;
			}

		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			e.printStackTrace();
			throw businessException;
		}
		return test;
	}

	private void deleteArticleToSell(int articleId) throws BusinessException {
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_DELETE_ARTICLE);
			prepStmt.setInt(1, articleId);
			rs = prepStmt.executeQuery();
		}
		catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_ARTICLE_ECHEC);
			e.printStackTrace();
			throw businessException;
		}
	}

	// method to call the stored procedure : interrogating the db to see which auctions are over, marking the article status as sold, crediting seller, debiting buyer, affecting new user to sold article
	@Override
	public void updateDatabase() throws BusinessException {
		CallableStatement callStmt = null;
		boolean b = false;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			callStmt = cnx.prepareCall("{call updateArticle}");
			int i = callStmt.executeUpdate();
			if (i > 0) {
				b = true;
			}
			System.out.println("Database updated : " + b);
		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			e.printStackTrace();
			throw businessException;
		}
	}

	/**
	 * BIDS : MAIN METHODS (Select, Create, Update, Delete)
	 */
	//
	@Override
	public void updateBid(int sessionId, int articleId, Integer myOffer, LocalDateTime date) throws BusinessException {
		PreparedStatement prepStmt = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_UPDATE_BID);
			prepStmt.setInt(1, myOffer);
			prepStmt.setTimestamp(2, Timestamp.valueOf(date));
			prepStmt.setInt(3, sessionId);
			prepStmt.setInt(4, articleId);
			prepStmt.executeUpdate();
		}
		catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJECT_ECHEC);
			e.printStackTrace();
			throw businessException;
		}

		updateSellPrice(articleId, myOffer);
	}

	private void updateSellPrice(int articleId, Integer myOffer) throws BusinessException {
		PreparedStatement prepStmt = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_UPDATE_SELL_PRICE);
			prepStmt.setInt(1, myOffer);
			prepStmt.setInt(2, articleId);
			prepStmt.executeUpdate();

		}
		catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJECT_ECHEC);
			e.printStackTrace();
			throw businessException;
		}
	}

	@Override
	public void createBid(int sessionId, int articleId, Integer myOffer, LocalDateTime date) throws BusinessException {
		PreparedStatement prepStmt = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			prepStmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_CREATE_BID);
			prepStmt.setInt(1, sessionId);
			prepStmt.setInt(2, articleId);
			prepStmt.setTimestamp(3, Timestamp.valueOf(date));
			prepStmt.setInt(4, myOffer);
			prepStmt.executeUpdate();
		}
		catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJECT_ECHEC);
			e.printStackTrace();
			throw businessException;
		}

		updateSellPrice(articleId, myOffer);

	}

	@Override
	public boolean checkbidExist(int sessionId, int articleId) throws BusinessException {
		boolean bidExists = false;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {

			pstmt = cnx.prepareStatement(SQL_REQUESTS_Utils.SQL_SELECT_BID);
			pstmt.setInt(1, sessionId);
			pstmt.setInt(2, articleId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				bidExists = true;
			}

		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.READ_ERROR);
			e.printStackTrace();
		}


		return bidExists;
	}


}
