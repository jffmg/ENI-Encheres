package fr.eni.ecole.trocenchere.bll;

import java.util.List;

import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.dal.DAOFactory;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

public class ArticleManager {
	
private DAO articleDao;
	
	public ArticleManager() {
		this.articleDao = DAOFactory.getArticle();
	}
	
	//method in order to get articles when not connected
	public List<Article> selectArticlesEC(String keyWord, int category) throws BusinessException {
		return this.articleDao.selectArticlesEC(keyWord, category);
	}

}
