package fr.eni.ecole.trocenchere.servlets;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.trocenchere.bll.ArticleManager;
import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

@WebServlet("/ServletNonConnectedHome")
public class ServletNonConnectedHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static List<Integer> listeCodesErreur = new ArrayList<>();

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
		// check: if user is connected, dispatch to ConnectedHome
		String userName = request.getParameter("user");
		if (userName != null) {
			this.getServletContext().getRequestDispatcher("/ServletConnectedHome").forward(request, response);
		} else {

			// displaying the NonConnectedHome, with the object CATEGORIES

			// displaying articles (default: no keyword, category Toutes)
			int categorySelected = 0;
			String keyWord = "";

			List<Article> articlesSelected = null;
			try {
				articlesSelected = displayArticles(keyWord, categorySelected, request);
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				e.printStackTrace();
			}

			request.getServletContext().setAttribute("articlesSelected", articlesSelected);

			this.getServletContext().getRequestDispatcher("/WEB-INF/NonConnectedHome.jsp").forward(request, response);
		}
}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		// getting the parameters : category selected by user
		String categorySelectedString = request.getParameter("categories");
		String categorySelectedStringStripAccents = stripAccents(categorySelectedString);
		int categorySelected = 0;

		// changing labels to int
		switch (categorySelectedStringStripAccents.toLowerCase().trim()) {
		case "informatique":
			categorySelected = 1;
			break;
		case "ameublement":
			categorySelected = 2;
			break;
		case "vatement":
			categorySelected = 3;
			break;
		case "sport & loisirs":
			categorySelected = 4;
			break;
			// default = all selected
		default:
			categorySelected = 0;
			break;
		}

		// getting the keyword typed by user
		String keyWord = null;

		keyWord = request.getParameter("keyWord");
		System.out.println(keyWord);


		// displaying articles
		List<Article> articlesSelected = null;
		try {
			articlesSelected = displayArticles(keyWord, categorySelected, request);
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			e.printStackTrace();
		}

		request.getServletContext().setAttribute("articlesSelected", articlesSelected);

		this.getServletContext().getRequestDispatcher("/WEB-INF/NonConnectedHome.jsp").forward(request, response);

	}

	private String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[^\\p{ASCII}]", "");
		return s;
	}

	// function to select the articles to display according to user's choices
	public static List<Article> displayArticles(String keyWord, int categorySelected, HttpServletRequest request)
			throws BusinessException {
		ArticleManager articleManager = new ArticleManager();
		List<Article> articlesSelected = null;

		articlesSelected = articleManager.selectArticlesEC(keyWord, categorySelected);
		return articlesSelected;
	}

}
