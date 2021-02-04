package fr.eni.ecole.trocenchere.gestion.erreurs;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesResultatDAL {

	/**
	 * Echec général quand tentative d'ajouter un objet null
	 */
	public static final int INSERT_OBJET_NULL=10000;

	/**
	 * Echec général quand erreur non gérée à l'insertion
	 */
	public static final int INSERT_OBJET_ECHEC=10001;

	/**
	 * Echec général à la modification d'un enregistrement
	 */
	public static final int UPDATE_OBJECT_ECHEC = 10005;

	/**
	 * Echec de la lecture des listes
	 */
	public static final int READ_ERROR = 10002;

	// Echec lors de l'effacement de données
	public static final int DELETE_ARTICLE_ECHEC=10003;

	public static final int DELETE_LISTE_ECHEC=10004;
	
	public static final int DELETE_USER_ECHEC=10006;

}