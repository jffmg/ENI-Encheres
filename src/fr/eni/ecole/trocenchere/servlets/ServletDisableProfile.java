package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.utils.ServletUtils;

@WebServlet("/Connected/DisableProfile")
public class ServletDisableProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// getting the object CATEGORIES so they're available in the servlet
		String categoriesString = this.getServletContext().getInitParameter("CATEGORIES");
		List<String> categories = Arrays.asList(categoriesString.split(","));

		this.getServletContext().setAttribute("categories", categories);
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// get the user data
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("user");
		//System.out.println("Session of " + userName);
		
		// try to delete the user
		boolean deletionOK = false;
		UserManager user = new UserManager();
		try {
			user.disableUser(userName);
			deletionOK = true;
		}catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
			
			//System.out.println("could not delete user: " + userName + " -> redirecting to profile");
		}
		
		if (deletionOK) {
			//System.out.println("TEST");

			// if OK delete session attribute user
			session.removeAttribute("user");

			// if Ok dispatch to NonConnectedHome, with the object CATEGORIES
			this.getServletContext().getRequestDispatcher("/Home").forward(request, response);
		}
		else {
			// if not OK dispatch to profile
			this.getServletContext().getRequestDispatcher("/Profile?profile=" + userName).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
