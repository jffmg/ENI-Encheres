package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.utils.PasswordUtils;
import fr.eni.ecole.trocenchere.utils.ServletUtils;

/**
 * Servlet implementation class ServletSignUp
 */
@WebServlet("/SignUp")
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
		String passwordEncrypted = PasswordUtils.encrypt(password);

		// 3 - create user
		UserManager userManager = new UserManager();
		try {
			userManager.addUser(user, name, firstName, email, phone, street, postCode, city, passwordEncrypted);
		} catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
			//System.out.println("erreur lors de la saisie du formulaire");
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/SignUpForm.jsp");
			rd.forward(request, response);
			e.printStackTrace();
		}

		// 4 - if signing form OK

		// Save user for the session
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		//System.out.println("saisie du formulaire OK");

		//Redirect to Home
		request.getServletContext().getRequestDispatcher("/Connected/Home?foo=get").forward(request, response);
		//request.getServletContext().getRequestDispatcher("/WEB-INF/ConnectedHome.jsp").forward(request, response);

		//response.sendRedirect("/ServletConnectedHome");
		//response.sendRedirect("/WEB-INF/ConnectedHome.jsp");
	}

}
