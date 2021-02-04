package fr.eni.ecole.trocenchere.bll;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.dal.DAOFactory;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

public class ArticleManager {

	private DAO articleDao;

	public ArticleManager() {
		this.articleDao = DAOFactory.getArticle();
	}
/*
	// method in order to get articles when not connected
	public List<Article> selectArticlesEC(String keyWord, int category) throws BusinessException {
		return this.articleDao.selectArticlesEC(keyWord, category);
	}
*/
	// method in order to get articles when connected
	/*public List<Article> selectPurchases(String keyWord, int category, boolean openBids, boolean myBids,
			boolean myWonBids) throws BusinessException {
		return this.articleDao.selectPurchases(keyWord, category, openBids, myBids, myWonBids);
	}

	public List<Article> selectSales(String keyWord, int category, boolean currentSales, boolean notStartedSales,
			boolean endedSales) throws BusinessException {
		return this.articleDao.selectSales(keyWord, category, currentSales, notStartedSales, endedSales);
	}*/

	public List<Article> displayArticles(String keyword, String category, HttpServletRequest request) throws BusinessException {
		return this.articleDao.displayArticles(keyword, category, request);
	}
	
	public List<Article> displayArticlesConnected(String userName, String keyword, String category, String buyOrSell, String checkBox, HttpServletRequest request) throws BusinessException{
		return this.articleDao.displayArticlesConnected(userName, keyword, category, buyOrSell, checkBox, request);
	}

}
