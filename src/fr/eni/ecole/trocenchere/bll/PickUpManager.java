package fr.eni.ecole.trocenchere.bll;

import fr.eni.ecole.trocenchere.bo.Article;
import fr.eni.ecole.trocenchere.bo.PickUp;
import fr.eni.ecole.trocenchere.dal.DAO;
import fr.eni.ecole.trocenchere.dal.DAOFactory;
import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

public class PickUpManager {
	
	private DAO pickUpDao;

	public PickUpManager() {
		this.pickUpDao = DAOFactory.getPickUpInfo();
	}

	public PickUp getPickUp(String articleID) throws BusinessException{
		return this.pickUpDao.selectPickUp(articleID);
	}

}
