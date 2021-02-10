package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.trocenchere.bll.ArticleManager;
import fr.eni.ecole.trocenchere.bll.PickUpManager;
import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.PickUp;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.gestion.erreurs.CodesResultatServlets;
import fr.eni.ecole.trocenchere.utils.ServletUtils;

/**
 * Servlet implementation class ServletSellArticle
 */
@WebServlet("/Connected/SellArticle")
public class ServletSellArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//System.out.println("doget - servlet sellArticle");

		manageProfile(request);

		String articleID = request.getParameter("articleID");
		
		//System.out.println("je passe dans la ServletSellArticle - doget / articleID : " + articleID);
		
		//System.out.println("message: " + request.getParameter("message"));
		//System.out.println("message: " + request.getServletContext().getAttribute("message"));
		//System.out.println("message: " + request.getAttribute("message"));
		//System.out.println("message: " + request.getSession().getAttribute("message"));

		manageArticle(request, articleID);
		managePickUp(request, articleID);

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/SellArticle.jsp");
		rd.forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		
		boolean hasError = false;
		
		//System.out.println("dopost - servlet sellArticle");

		String profileName = request.getParameter("profile");
		LocalDateTime startDate = null;
		LocalDateTime endDate = null;
		List<Integer> listeCodesErreur = new ArrayList<>();

		// Get parameters
		String articleId = request.getParameter("articleId");
		String articleName = request.getParameter("name");
		String articleDesc = request.getParameter("description");
		String articleCat = request.getParameter("categories");
		Integer saleStartBid = Integer.parseInt(request.getParameter("startBid"));
		String saleStartDate = request.getParameter("startDate");
		String saleEndDate = request.getParameter("endDate");
		String pickUpStreet = request.getParameter("street");
		String pickUpPostCode = request.getParameter("postCode");
		String pickUpCity = request.getParameter("city");
		
		//Error category
		if ("Toutes".equals(articleCat)) {
			listeCodesErreur.add(CodesResultatServlets.CATEGORY_MISSING_ERROR);
			hasError=true;
		}

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("user");
		User profile=null;
		UserManager um = new UserManager();
		
		try {
			profile = um.selectUser(userName);
		}
		catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
			hasError=true;
		}
				
		//get session data
		int idSeller = profile.getIdUser();

		//format dates
		try {
			saleStartDate = saleStartDate.replace("T", " ");
			saleEndDate = saleEndDate.replace("T", " ");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			startDate = LocalDateTime.parse(saleStartDate, dtf);
			endDate = LocalDateTime.parse(saleEndDate, dtf);
			//System.out.println("Start date : " + startDate + " / End date : " + endDate); //debug display
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_DATETIME_ERROR);
			hasError=true;
		}
		
		// create Article
		try {
			ArticleManager am = new ArticleManager();

			//System.out.println("articleId: " + articleId);
			
			if (articleId == null || "".equals(articleId)) {
				am.sellArticle(idSeller, articleName, articleDesc, articleCat, saleStartBid, startDate, endDate,
					pickUpStreet, pickUpPostCode, pickUpCity);
			}
			else {
				am.updateArticle(articleId, idSeller, articleName, articleDesc, articleCat, saleStartBid, startDate, endDate,
						pickUpStreet, pickUpPostCode, pickUpCity);
			}
		} catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
			//System.out.println("erreur lors de la saisie du formulaire");
			e.printStackTrace();
			hasError=true;
		}
		
		if (hasError) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		}
		else {
			String message = "Votre vente a bien été ajoutée";
			if (articleId != null) {
				message = "Votre vente a bien été mise à jour";
			}
			request.setAttribute("message", message);
		}
		
		if (articleId == null) {
			this.doGet(request, response);
		}
		else {
			manageArticle(request, articleId);
			managePickUp(request, articleId);
			
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/SellArticle.jsp");
			rd.forward(request, response);
		}
	}
	
	/*
	* functions
	*/
	private void manageProfile(HttpServletRequest request) {
		String profileName = request.getParameter("profile");

		System.out.println("je passe dans la ServletSellArticle - doGet / profilename : " + profileName);

		// User - link to data base
		UserManager userManager = new UserManager();
		User profile = null;

		try {
			profile = userManager.selectUser(profileName);
		} catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
		}

		request.getServletContext().setAttribute("profile", profile);
		// System.out.println("Ville du profile : " + profile.getCity());
	}
	
	private void manageArticle(HttpServletRequest request, String articleID) {
		ArticleManager am = new ArticleManager();
		Article article = null;

		try {
			article = am.selectArticle(articleID);
		} catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
		}

		request.getServletContext().setAttribute("article", article);
	}
	
	private void managePickUp(HttpServletRequest request, String articleID){
		PickUpManager pm = new PickUpManager();
		PickUp pickUp = null;

		try {
			pickUp = pm.getPickUp(articleID);
		} catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
		}


		request.getServletContext().setAttribute("pickUp", pickUp);
	}
	
}
