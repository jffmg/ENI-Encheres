package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.utils.ServletUtils;


@WebServlet("/Profile")
public class ServletProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String profileName = request.getParameter("profile");

		//System.out.println("je passe dans la ServletProfile - doGet / profilename : " +profileName);

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
		// System.out.println("Ville du profil : " + profile.getCity());

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/Profile.jsp");
		rd.forward(request, response);

	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("je passe dans la ServletProfile - doPost");
		doGet(request, response);
	}

}
