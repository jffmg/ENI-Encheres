package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

/**
 * Servlet implementation class ServletSignUp
 */
@WebServlet("/ServletSignUp")
public class ServletSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// display sign-up screen
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/SignUpForm.jsp");
		rd.forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur=new ArrayList<>();

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

		// 3 - create user
		UserManager userManager = new UserManager();
		try {
			userManager.addUser(user, name, firstName, email, phone, street, postCode, city, passwordEncrypted);
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
			e.printStackTrace();
		}

		// 4 - if error dispatch on sign form again with error message
		if(listeCodesErreur.size()>0)
		{
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/SignUpForm.jsp");
			rd.forward(request, response);
		}
		
		// 4 - if signing form OK
		else {
			// Save user for the session
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			//Redirect to Home
			request.getServletContext().getRequestDispatcher("/ServletConnectedHome?foo=get").forward(request, response);
			//request.getServletContext().getRequestDispatcher("/WEB-INF/ConnectedHome.jsp").forward(request, response);

			//response.sendRedirect("/ServletConnectedHome");
			//response.sendRedirect("/WEB-INF/ConnectedHome.jsp");
		}

	}

	// TODO move to somewhere else to avoid duplication
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
