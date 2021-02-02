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
import javax.servlet.http.HttpSession;

import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocencheres.gestion.erreurs.CodesResultatServlets;


@WebServlet("/ServletConnection")
public class ServletConnection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/Connection.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur=new ArrayList<>();
		
		// get the paramaters
		String userName = request.getParameter("user");
		String password = request.getParameter("password");

		// encrypt password
		String passwordEncrypted = encrypt(password);
		
		// User - link to data base
		UserManager userManager = new UserManager();
		
		// Compare password
		 String passwordDataBase = userManager.getUser(userName).getPasswordEncrypted();
		if(passwordEncrypted.equals(passwordDataBase)) {
			// Save user for the session
	        HttpSession session = request.getSession();
	        session.setAttribute("user", userName);
			
			//Dispatch connected home
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/ConnectedHome.jsp");
			rd.forward(request, response);
		}
		else {
			// Message error
			listeCodesErreur.add(CodesResultatServlets.USER_PASSWORD_ERROR);
			
			// Dispatch connection page
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/Connection.jsp");
			rd.forward(request, response);
		}

	}

	//function to encrypt password
	private String encrypt(String password) {
		String passwordEncrypted="";
		for (int i=0; i<password.length();i++)  {
			int c=password.charAt(i)^48; 
			passwordEncrypted=passwordEncrypted+(char)c;
		}

		return passwordEncrypted;
	}

}
