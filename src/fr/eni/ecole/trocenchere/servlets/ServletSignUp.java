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

import fr.eni.ecole.trocenchere.BusinessException;
import fr.eni.ecole.trocenchere.bll.UserManager;
import fr.eni.ecole.trocenchere.bo.User;

/**
 * Servlet implementation class ServletSignUp
 */
@WebServlet("/ServletSignUp")
public class ServletSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/SignUpForm.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Integer> listeCodesErreur = new ArrayList<>();

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String firstName = request.getParameter("firstName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String street = request.getParameter("street");
		String postCode = request.getParameter("postCode");
		String city = request.getParameter("city");
		
		String passwordEncrypted = encrypt(password);
		
		

		System.out.println(userName + " " + password +" "+ passwordEncrypted + " " + name + " " + firstName + " " + email + " " + phone + " " + street
				+ " " + postCode + " " + city);
		
		UserManager um = new UserManager();
		try {
			User newUser = um.addUser(userName, name, firstName, email, phone, street, postCode, city, passwordEncrypted);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
	}
	
	private String encrypt(String password) {
		String passwordEncrypted="";
		for (int i=0; i<password.length();i++)  {
			int c=password.charAt(i)^48;
			passwordEncrypted=passwordEncrypted+(char)c;
		}

		return passwordEncrypted;
	}

}
