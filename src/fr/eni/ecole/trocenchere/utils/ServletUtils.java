package fr.eni.ecole.trocenchere.utils;

import javax.servlet.http.HttpServletRequest;

import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

public class ServletUtils {

	public static void handleBusinessException(BusinessException be, HttpServletRequest request) {
		request.setAttribute("listeCodesErreur", be.getListeCodesErreur());
		
		be.printStackTrace();
	}
}
