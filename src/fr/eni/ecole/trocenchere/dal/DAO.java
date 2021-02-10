package fr.eni.ecole.trocenchere.dal;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.Bid;
import fr.eni.ecole.trocenchere.bo.PickUp;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

public interface DAO {

	// userDAO methods
	public abstract User selectUser(String username) throws BusinessException;
	public abstract void createUser(User data) throws BusinessException;
	public abstract void updateUser(User data) throws BusinessException;
	public abstract boolean checkUser(String user) throws BusinessException;
	public abstract boolean checkEmail(String email) throws BusinessException;
	public abstract void disableUser(String userName) throws BusinessException;
	public abstract boolean checkArticlesToSale(String userName) throws BusinessException;
	public abstract int selectPoints(int sessionId) throws BusinessException;

	//Articles DAO Methods
	public abstract List<Article> displayArticles(String keyword, String category, HttpServletRequest request)
			throws BusinessException;

	public abstract List<Article> displayArticlesConnected(String userName, String keyword, String category,
			String buyOrSell, String checkBox, HttpServletRequest request) throws BusinessException;

	public abstract void createSellNewArticle(int userId, Article articleToSell, PickUp pickUp) throws BusinessException;
	public abstract Article selectArticle(String articleID) throws BusinessException;

	public abstract void updateDatabase() throws BusinessException;

	// Bid DAO methods
	public abstract void updateBid(int sessionId, int articleId, Integer myOffer, LocalDateTime date) throws BusinessException;
	public abstract void createBid(int sessionId, int articleId, Integer myOffer, LocalDateTime date) throws BusinessException;

	public abstract boolean checkbidExist(int sessionId, int articleId) throws BusinessException;
	public abstract Article updateArticle(Article articleToSell, PickUp pickUp) throws BusinessException;

	
	// PickUp DAO methods
	public abstract PickUp selectPickUp(String articleID) throws BusinessException;

	public abstract void updatePoints(int sessionId, Integer myOffer) throws BusinessException;
	public abstract Bid selectBidByArticleAndAmount(int articleId, int currentBid) throws BusinessException;
	public abstract User selectUserById(int bidderId) throws BusinessException;
	public abstract void raisePoints(int bidderId, int currentBid) throws BusinessException;






}
