package fr.eni.ecole.trocenchere.bll;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.PickUp;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.dal.DAOFactory;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.gestion.erreurs.CodesResultatBLL;
import fr.eni.ecole.trocenchere.utils.DalUtils;

public class ArticleManager {

	private DAO articleDao;

	public ArticleManager() {
		this.articleDao = DAOFactory.getArticle();
	}
	/*
	 * // method in order to get articles when not connected public List<Article>
	 * selectArticlesEC(String keyWord, int category) throws BusinessException {
	 * return this.articleDao.selectArticlesEC(keyWord, category); }
	 */
	// method in order to get articles when connected
	/*
	 * public List<Article> selectPurchases(String keyWord, int category, boolean
	 * openBids, boolean myBids, boolean myWonBids) throws BusinessException {
	 * return this.articleDao.selectPurchases(keyWord, category, openBids, myBids,
	 * myWonBids); }
	 * 
	 * public List<Article> selectSales(String keyWord, int category, boolean
	 * currentSales, boolean notStartedSales, boolean endedSales) throws
	 * BusinessException { return this.articleDao.selectSales(keyWord, category,
	 * currentSales, notStartedSales, endedSales); }
	 */

	public List<Article> displayArticles(String keyword, String category, HttpServletRequest request)
			throws BusinessException {
		return this.articleDao.displayArticles(keyword, category, request);
	}

	public List<Article> displayArticlesConnected(String userName, String keyword, String category, String buyOrSell,
			String checkBox, HttpServletRequest request) throws BusinessException {
		return this.articleDao.displayArticlesConnected(userName, keyword, category, buyOrSell, checkBox, request);
	}

	public void sellArticle(int userId, String articleName, String articleDesc, String articleCat, Integer saleStartBid,
			LocalDateTime saleStartDate, LocalDateTime saleEndDate, String pickUpStreet, String pickUpPostCode,
			String pickUpCity) throws BusinessException{
		BusinessException be = new BusinessException();

		// category from String to int
		int idCategory = DalUtils.categoryStringToInteger(articleCat);

		// check the end of sale date
		boolean datesAreOkay = false;
		datesAreOkay = checkDates(saleEndDate, saleStartDate);
		if (datesAreOkay == true) {
			be.ajouterErreur(CodesResultatBLL.SALE_END_DATE);
			System.out.println(CodesResultatBLL.SALE_END_DATE);
		}

		// define the sale status when created the sellArticle
		String status = null;

		if (saleStartDate.isBefore(LocalDateTime.now())){
			status = "EC";
		}
		else {
			status = "CR";
		}

		PickUp pickUp = new PickUp(pickUpStreet, pickUpPostCode, pickUpCity);
		Article articleToSell = new Article (articleName, articleDesc, saleStartDate, saleEndDate, saleStartBid,status, idCategory, userId);

		if (!be.hasErreurs()) {
			this.articleDao.createSellNewArticle(userId, articleToSell, pickUp);
		}

		if (be.hasErreurs()) {
			throw be;
		}
	}
	private boolean checkDates(LocalDateTime saleEndDate, LocalDateTime saleStartDate) {
		boolean datesAreOkay = true;
		boolean isSaleEndDateBeforeActualDate = saleEndDate.isBefore(LocalDateTime.now());
		boolean isSaleStartDateBeforeEndDate = saleStartDate.isBefore(saleEndDate);
		if (!isSaleEndDateBeforeActualDate || !isSaleStartDateBeforeEndDate) {
			datesAreOkay = false;
		}

		return datesAreOkay;
	}

	public Article selectArticle(String articleID) throws BusinessException {
		return this.articleDao.selectArticle(articleID);
	}

	public String selectCategoryString(int idCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateDatabase() throws BusinessException {
		this.articleDao.updateDatabase();
	}

}
