package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.eni.ecole.trocenchere.bll.ArticleManager;
import fr.eni.ecole.trocenchere.bll.BidManager;
import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.utils.DalUtils;
import fr.eni.ecole.trocenchere.utils.ServletUtils;

/**
 * Servlet implementation class ServletBid
 */
@WebServlet("/Bid")
public class ServletBid extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String profileName = request.getParameter("profile");
		String articleID = request.getParameter("articleID");
		
		//System.out.println("je passe dans la ServletBid - doGet / profile name : " + profileName + " articleID : " + articleID);
		
		// User - link to data base
		UserManager userManager = new UserManager();
		User profile=null;
		
		try {
			profile = userManager.selectUser(profileName);
		}
		catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
		}
		
		ArticleManager articleManager = new ArticleManager();
		Article currentArticle = null;
		String currentCat = null;
		
		try {
			currentArticle = articleManager.selectArticle(articleID);
		}
		catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
		}
		System.out.println("currentArticle nom : " + currentArticle.getName());
		
		LocalDateTime endDate = currentArticle.getBidEndDate();
		LocalDateTime startDate = currentArticle.getBidStartDate();
		
		String endDateString = DalUtils.dateFormatterDateToString(endDate);
		
		request.getServletContext().setAttribute("profile", profile);
		request.getServletContext().setAttribute("currentArticle", currentArticle);
		request.getServletContext().setAttribute("endDateString", endDateString);
		request.getServletContext().setAttribute("articleID", articleID);
		//System.out.println("Ville du profile : " + profile.getCity());
		
		// Parameter : test is auction has started or not yet
		boolean hasAuctionStarted = true;
		LocalDateTime now = LocalDateTime.now();
		if(now.isBefore(startDate)) {
			hasAuctionStarted = false;
		}
		
		request.getServletContext().setAttribute("hasAuctionStarted", hasAuctionStarted);

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/Bid.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur = new ArrayList<>();
		boolean hasError = false;
		
		String profileName = request.getParameter("profile");
		String articleID = request.getParameter("articleID");
		request.getServletContext().setAttribute("articleID", articleID);
		//System.out.println("je passe dans la ServletUpdateProfile - doPost / profile name : " + profileName + " articleID : " + articleID );
		
		// User - link to data base
		UserManager userManager = new UserManager();
		User profile=null;
		
		try {
			profile = userManager.selectUser(profileName);
		}
		catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
			hasError=true;
		}
		
		int sessionID = profile.getIdUser();
		
		Integer myOffer = Integer.parseInt(request.getParameter("myOffer"));
		Integer currentOffer = Integer.parseInt(request.getParameter("currentOffer"));
		Integer startingBid = Integer.parseInt(request.getParameter("startingBid"));
		Integer articleIdInteger = Integer.parseInt(articleID);
		//System.out.println("Article Integer : " + articleIdInteger);
		
		BidManager bm = new BidManager();
		
		try {
			bm.updateMaxBid(sessionID, articleIdInteger, myOffer, currentOffer, startingBid);
		} catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
			//System.out.println("erreur lors de la saisie de l'offre");
			hasError=true;
		}
		//Dispatch
		if (hasError) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			//System.out.println("ERROR => doit afficher message sur jsp");
		}
		else {
			String message = "Votre enchère a bien été ajoutée";
			request.setAttribute("message", message);
			//System.out.println("OK => doit afficher message sur jsp");
		}
		
		this.doGet(request, response);
	}

}
