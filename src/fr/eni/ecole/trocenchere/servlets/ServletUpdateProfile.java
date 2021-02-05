package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.bo.Password;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

/**
 * Servlet implementation class ServletUpdateProfile
 */
@WebServlet("/ServletUpdateProfile")
public class ServletUpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String profileName = request.getParameter("profile");
		
		// User - link to data base
		UserManager userManager = new UserManager();
		User profile=null;
		
		try {
			profile = userManager.selectUser(profileName);
		}catch (BusinessException e) {
			request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
			e.printStackTrace();
		}
		
		request.getServletContext().setAttribute("profile", profile);
		//System.out.println("Ville du profile : " + profile.getCity());

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/UpdateProfile.jsp");
		rd.forward(request, response);

	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String profileName = request.getParameter("profile");
		
		// User - link to data base
				UserManager userManager = new UserManager();
				User profile=null;
				
				try {
					profile = userManager.selectUser(profileName);
				}catch (BusinessException e) {
					request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
					e.printStackTrace();
				}
				
		//get session data
		String sessionUser = profile.getUser();
		String sessionEmail = profile.getEmail();
		int sessionID = profile.getIdUser();
		
		// 1 - get the parameters
		String user = request.getParameter("userName");
		String name = request.getParameter("name");
		String firstName = request.getParameter("firstName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String street = request.getParameter("street");
		String postCode = request.getParameter("postCode");
		String city = request.getParameter("city");
		String password = request.getParameter("newPassword");
		
		
		

		// 2 - encrypt password
		Password ps = new Password();
		String passwordEncrypted = ps.encrypt(password);

		// 3 - update user
		UserManager um = new UserManager();
		try {
			um.updateUser(user, name, firstName, email, phone, street, postCode, city, passwordEncrypted, sessionUser, sessionEmail, sessionID);
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			System.out.println("erreur lors de la saisie du formulaire");
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/ServletNonConnected");
			rd.forward(request, response);
			e.printStackTrace();
		}
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/ServletProfile");
		rd.forward(request, response);
		
	}

}
