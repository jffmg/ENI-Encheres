package fr.eni.ecole.trocenchere.dal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

public interface DAO {

	// userDAO methods
	public abstract User selectUser(String username) throws BusinessException;

	public abstract void createUser(User data) throws BusinessException;

	public abstract void updateUser(User data) throws BusinessException;

	public abstract boolean checkUser(String user) throws BusinessException;

	public abstract boolean checkEmail(String email) throws BusinessException;

	// articleDAO methods
	// public abstract List<Article> selectArticlesEC(String keyWord, int category)
	// throws BusinessException;
	/*
	 * public abstract List<Article> selectPurchases(String keyWord, int category,
	 * boolean openBids, boolean myBids, boolean myWonBids) throws BusinessException
	 * ; public abstract List<Article> selectSales(String keyWord, int category,
	 * boolean currentSales, boolean notStartedSales, boolean endedSales) throws
	 * BusinessException ;
	 */

	public abstract List<Article> displayArticles(String keyword, String category, HttpServletRequest request)
			throws BusinessException;

	public abstract List<Article> displayArticlesConnected(String userName, String keyword, String category, String buyOrSell, String checkBox, HttpServletRequest request)
			throws BusinessException;

}
