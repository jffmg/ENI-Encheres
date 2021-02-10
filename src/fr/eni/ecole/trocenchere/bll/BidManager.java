package fr.eni.ecole.trocenchere.bll;

import java.time.LocalDateTime;

import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.Bid;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.dal.DAOFactory;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.gestion.erreurs.CodesResultatBLL;
import fr.eni.ecole.trocenchere.utils.DalUtils;

public class BidManager {

	private DAO bidDao;

	public BidManager() {
		this.bidDao = DAOFactory.getBid();
	}

	public void updateMaxBid(int sessionId, int articleId, Integer myOffer, Integer currentOffer, Integer startingBid)
			throws BusinessException {
		BusinessException be = new BusinessException();

		LocalDateTime date = LocalDateTime.now();
		
		//check the bidder has enough points
		boolean checkPointTest = checkPoints(sessionId, myOffer);
		if (!checkPointTest) {
			be.ajouterErreur(CodesResultatBLL.MY_OFFER_INF_MY_POINTS);
			System.out.println(CodesResultatBLL.MY_OFFER_INF_MY_POINTS);
			throw be;
		} else {
			//check if there is already a winning bid
			selectArticleMaxBid(articleId);
			// check if a bid already exists for this article and this user
			boolean isBidinBase = this.bidDao.checkbidExist(sessionId, articleId);
			if (isBidinBase == false) {
				createBid(sessionId, articleId, myOffer, startingBid, date);
			} else {
				updateBid(sessionId, articleId, myOffer, currentOffer, date);
			}
		}
		
		//withdraw points on the bidder account
		debitUserPoints(sessionId, myOffer);
		
		//check if there is already a winning bid
		//selectArticleMaxBid(articleId);

		// check the points of the bider
		/*
		 * boolean checkPointTest = checkPoints(sessionId, myOffer); if
		 * (!checkPointTest) {
		 * be.ajouterErreur(CodesResultatBLL.MY_OFFER_INF_MY_POINTS);
		 * System.out.println(CodesResultatBLL.MY_OFFER_INF_MY_POINTS); throw be; } else
		 * {
		 * 
		 * // if first bid for this article if( currentOffer == null || currentOffer ==
		 * 0) { createBid(sessionId, articleId, myOffer, startingBid, date); } // if it
		 * is not the first bid else { updateBid(sessionId, articleId, myOffer,
		 * currentOffer, date);
		 * 
		 * } }
		 */
	}

	private void selectArticleMaxBid(int articleId) throws BusinessException {
		String articleIdString = String.valueOf(articleId);
		Article article = this.bidDao.selectArticle(articleIdString);
		int currentBid = article.getSalePrice();
		int bidderId;
		User previousBidder = new User();
		if (currentBid != 0) {
			//select concerned bid and bid's owner
			Bid previousBid = this.bidDao.selectBidByArticleAndAmount(articleId, currentBid);
			bidderId = previousBid.getUserId();
			previousBidder = this.bidDao.selectUserById(bidderId);
			//credit previous bidder's point with his own bid amount
			this.bidDao.raisePoints(bidderId, currentBid);
		}
		
	}

	
	private void debitUserPoints(int sessionId, Integer myOffer) throws BusinessException  {
		UserManager um = new UserManager();
		um.updatePointsUser(sessionId, myOffer);
	}

	private boolean checkPoints(int sessionId, int myOffer) throws BusinessException {
		UserManager user = new UserManager();
		int pointsUser = user.selectPointsUser(sessionId);

		return (pointsUser >= myOffer);
	}

	public void createBid(int sessionId, int articleId, Integer myOffer, Integer startingBid, LocalDateTime date)
			throws BusinessException {
		BusinessException be = new BusinessException();

		if (myOffer < startingBid) {
			be.ajouterErreur(CodesResultatBLL.MY_OFFER_SUP_STARTING_BID);
			System.out.println(CodesResultatBLL.MY_OFFER_SUP_STARTING_BID);
		}

		if (be.hasErreurs()) {
			throw be;
		} else {
			this.bidDao.createBid(sessionId, articleId, myOffer, date);
		}
	}

	public void updateBid(int sessionId, int articleId, Integer myOffer, Integer currentOffer, LocalDateTime date)
			throws BusinessException {
		BusinessException be = new BusinessException();

		// if there is already some bids for this article
		if (myOffer < currentOffer) {
			be.ajouterErreur(CodesResultatBLL.MY_OFFER_SUP_CURRENT_OFFER);
			System.out.println(CodesResultatBLL.MY_OFFER_SUP_CURRENT_OFFER);
		}

		if (be.hasErreurs()) {
			throw be;
		} else {
			this.bidDao.updateBid(sessionId, articleId, myOffer, date);
		}
	}

}
