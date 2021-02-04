package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.trocenchere.bll.UserManager;
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1 - get the parameters
		String user = request.getParameter("userName");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String firstName = request.getParameter("firstName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String street = request.getParameter("street");
		String postCode = request.getParameter("postCode");
		String city = request.getParameter("city");

		// 2 - encrypt password
		String passwordEncrypted = encrypt(password);

		// 3 - update user
		UserManager userManager = new UserManager();
		try {
			userManager.changeUser(user, name, firstName, email, phone, street, postCode, city, passwordEncrypted);
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			System.out.println("erreur lors de la saisie du formulaire");
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/ServletNonConnected");
			rd.forward(request, response);
			e.printStackTrace();
		}
	}

	// TODO move to somewhere else to avoid duplication?
	// function to encrypt password
	private String encrypt(String password) {
		String passwordEncrypted = "";
		for (int i = 0; i < password.length(); i++) {
			int c = password.charAt(i) ^ 48;
			passwordEncrypted = passwordEncrypted + (char) c;
		}

		return passwordEncrypted;
	}

}
