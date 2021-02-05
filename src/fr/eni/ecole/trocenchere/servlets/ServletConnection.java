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
import fr.eni.ecole.trocenchere.bo.Password;
import fr.eni.ecole.trocenchere.bo.User;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;
import fr.eni.ecole.trocenchere.gestion.erreurs.CodesResultatServlets;


@WebServlet("/ServletConnection")
public class ServletConnection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/Connection.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur=new ArrayList<>();

		// get the paramaters
		String userName = request.getParameter("user");
		String password = request.getParameter("password");
		System.out.println("password :" + password);


		// encrypt password
		//String passwordEncrypted = encrypt(password);
		Password ps = new Password();
		String passwordEncrypted=ps.encrypt(password);
		System.out.println("paswwordEncrypted: " + passwordEncrypted);

		// User - link to data base
		UserManager userManager = new UserManager();
		String passwordDataBase = null;

		// Test user null
		User user=null;
		try {
			user = userManager.selectUser(userName);
		}catch (BusinessException e) {
			request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
			e.printStackTrace();
		}
		if (user != null) {
			passwordDataBase = user.getPasswordEncrypted();
		}
		System.out.println("paswwordDataBAse : " + passwordDataBase);

		// Compare password
		if (passwordDataBase != null && passwordDataBase.equals(passwordEncrypted)) {
			// Save user for the session
			HttpSession session = request.getSession();
			session.setAttribute("user", userName);

			if(listeCodesErreur.size()>0)
			{
				request.setAttribute("listeCodesErreur",listeCodesErreur);
			}

			//Dispatch connected home
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/ConnectedHome.jsp");
			rd.forward(request, response);
		}
		else {
			// Message error
			listeCodesErreur.add(CodesResultatServlets.USER_PASSWORD_ERROR);

			if(listeCodesErreur.size()>0)
			{
				request.setAttribute("listeCodesErreur",listeCodesErreur);
			}

			// Dispatch connection page
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/Connection.jsp");
			rd.forward(request, response);
		}

	}

}
