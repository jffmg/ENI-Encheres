package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.utils.PasswordUtils;
import fr.eni.ecole.trocenchere.utils.ServletUtils;

/**
 * Servlet implementation class ServletUpdateProfile
 */
@WebServlet("/Connected/UpdateProfile")
public class ServletUpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String profileName = request.getParameter("profile");
		
		System.out.println("je passe dans la ServletUpdateProfile - doGet / profile name : " + profileName );
		
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

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/UpdateProfile.jsp");
		rd.forward(request, response);

	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String profileName = request.getParameter("profile");
		
		//System.out.println("je passe dans la ServletUpdateProfile - doPost / profile name : " + profileName );
		
		// User - link to data base
		UserManager userManager = new UserManager();
		User profile=null;
		
		try {
			profile = userManager.selectUser(profileName);
		}
		catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
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
		String passwordEncrypted = PasswordUtils.encrypt(password);

		// 3 - update user
		UserManager um = new UserManager();
		try {
			// try update and set the new profile
			//Update user
			um.updateUser(user, name, firstName, email, phone, street, postCode, city, passwordEncrypted, sessionUser, sessionEmail, sessionID);
			
			//Update profile
			profile = um.selectUser(user);
			
			request.getSession().setAttribute("user", user);
			
			profileName = profile.getUser();
			
			//System.out.println("je dois avoir modifier le profil");
			
		} catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
			
			//System.out.println("erreur lors de la saisie du formulaire");
		}
		
		//Dispatch
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Profile?profile=" + profileName);
			rd.forward(request, response);
		
	}

}
