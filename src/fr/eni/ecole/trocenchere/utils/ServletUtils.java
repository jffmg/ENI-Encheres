package fr.eni.ecole.trocenchere.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.eni.ecole.trocenchere.gestion.erreurs.BusinessException;

public class ServletUtils {

	public static void handleBusinessException(BusinessException be, HttpServletRequest request) {
		handleErrors(be.getListeCodesErreur(), request);
	}

	public static void handleError(Integer newError, HttpServletRequest request) {
		List<Integer> errors = new ArrayList<Integer>();
		errors.add(newError);

		handleErrors(errors, request);
	}

	public static void handleErrors(List<Integer> newErrors, HttpServletRequest request) {
		
		List<Integer> errors = (List<Integer>) request.getAttribute("listeCodesErreur");
		
		if (errors == null) {
			errors = new ArrayList<Integer>();
			request.setAttribute("listeCodesErreur", errors);
		}
		
		errors.addAll(newErrors);

		System.out.println("--- ERRORS ---: ");
		for (Integer integer : errors) {
			System.out.println("-- error: " + integer);
		}
		
//		be.printStackTrace();
	}
}
