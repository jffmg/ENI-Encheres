package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.gestion.erreurs.CodesResultatServlets;
import fr.eni.ecole.trocenchere.utils.PasswordUtils;
import fr.eni.ecole.trocenchere.utils.ServletUtils;


@WebServlet("/Connection")
public class ServletConnection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Check if rememberMe cookie exists
				Cookie[] cookies = request.getCookies();
				String userNameRemembered = null;
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals("userNameRemembered")) {
							userNameRemembered = java.net.URLDecoder.decode(cookie.getValue(), "UTF-8");
							request.setAttribute("userNameRemembered", userNameRemembered);
							break;
						}
					}
				}
		
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/Connection.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get the paramaters
		String userName = request.getParameter("user");
		String password = request.getParameter("password");
		//System.out.println("password :" + password);
		
		//check if remember case is checked
		if (request.getParameter("rememberMe") != null) {
			//Create cookie + String to format HTM
			Cookie cookie = new Cookie("userNameRemembered", java.net.URLEncoder.encode(userName, "UTF-8"));
			cookie.setMaxAge(Integer.MAX_VALUE);
			cookie.setHttpOnly(true);
			response.addCookie(cookie);
		}

		// encrypt password
		//String passwordEncrypted = encrypt(password);
		String passwordEncrypted = PasswordUtils.encrypt(password);
		//System.out.println("paswwordEncrypted: " + passwordEncrypted);

		// User - link to data base
		UserManager userManager = new UserManager();
		String passwordDataBase = null;

		// Test user null
		User user=null;
		try {
			user = userManager.selectUser(userName);
		}catch (BusinessException e) {
			ServletUtils.handleBusinessException(e, request);
		}
		if (user != null) {
			passwordDataBase = user.getPasswordEncrypted();
		}
		//System.out.println("paswwordDataBAse : " + passwordDataBase);

		// Compare password
		if (passwordDataBase != null && passwordDataBase.equals(passwordEncrypted)) {
			// Save user for the session
			HttpSession session = request.getSession();
			session.setAttribute("user", userName);

			//Dispatch connected home
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/ConnectedHome.jsp");
			rd.forward(request, response);
		}
		else {
			// Message error
			ServletUtils.handleError(CodesResultatServlets.USER_PASSWORD_ERROR, request);

			// Dispatch connection page
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/Connection.jsp");
			rd.forward(request, response);
		}

	}

}
