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

import fr.eni.ecole.trocenchere.bll.ArticleManager;
import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;


@WebServlet("/Connected/Deconnection")
public class ServletDisconnected extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// getting the object CATEGORIES so they're available in the servlet
		String categoriesString = this.getServletContext().getInitParameter("CATEGORIES");
		List<String> categories = Arrays.asList(categoriesString.split(","));

		this.getServletContext().setAttribute("categories", categories);
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// delete session attribute user
		HttpSession session = request.getSession();
		session.removeAttribute("user");

		// dispatch to NonConnectedHome, with the object CATEGORIES
		this.getServletContext().getRequestDispatcher("/Home").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// function to select the articles to display according to user's choices
	public static List<Article> displayArticles(String keyWord, String categorySelected, HttpServletRequest request)
			throws BusinessException {
		ArticleManager articleManager = new ArticleManager();
		List<Article> articlesSelected = null;

		articlesSelected = articleManager.displayArticles(keyWord, categorySelected, request);
		return articlesSelected;
	}

}