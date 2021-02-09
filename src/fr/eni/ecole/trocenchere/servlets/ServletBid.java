package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;

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
		
		System.out.println("je passe dans la ServletBid - doGet / profile name : " + profileName );
		
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
		
		LocalDateTime endDate = currentArticle.getBidEndDate();
		
		String endDateString = DalUtils.dateFormatterDateToString(endDate);
		
		request.getServletContext().setAttribute("profile", profile);
		request.getServletContext().setAttribute("currentArticle", currentArticle);
		request.getServletContext().setAttribute("endDateString", endDateString);
		//System.out.println("Ville du profile : " + profile.getCity());

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/Bid.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
String profileName = request.getParameter("profile");
		
		System.out.println("je passe dans la ServletUpdateProfile - doPost / profile name : " + profileName );
		
		// User - link to data base
		UserManager userManager = new UserManager();
		User profile=null;
		
		try {
			profile = userManager.selectUser(profileName);
		}
		catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
		}
		
		int sessionID = profile.getIdUser();
		
		Integer myOffer = Integer.parseInt(request.getParameter("myOffer"));
		Integer currentOffer = Integer.parseInt(request.getParameter("currentOffer"));
		Integer startingBid = Integer.parseInt(request.getParameter("startingBid"));
		Integer articleId = Integer.parseInt(request.getParameter("articleId"));
		
		BidManager bm = new BidManager();
		
		try {
			bm.updateMaxBid(sessionID, articleId, myOffer, currentOffer, startingBid);
		} catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
			System.out.println("erreur lors de la saisie de l'offre");
		}
		//Dispatch
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Connected/Home?profile=" + profileName);
		rd.forward(request, response);
	}

}
