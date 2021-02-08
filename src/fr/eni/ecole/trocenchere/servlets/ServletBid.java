package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.trocenchere.bll.ArticleManager;
import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.utils.ServletUtils;

/**
 * Servlet implementation class ServletBid
 */
@WebServlet("/ServletBid")
public class ServletBid extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String profileName = request.getParameter("profile");
		
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
		
		request.getServletContext().setAttribute("profile", profile);
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
		
		Integer myOffer = Integer.parseInt(request.getParameter("userName"));
		
		ArticleManager am = new ArticleManager();
		
		try {
			am.updateMaxBid(sessionID, myOffer);
		} catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
			System.out.println("erreur lors de la saisie de l'offre");
		}
		//Dispatch
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/ServletConnectedHome?profile=" + profileName);
		rd.forward(request, response);
	}

}
