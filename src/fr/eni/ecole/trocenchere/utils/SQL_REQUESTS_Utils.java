package fr.eni.ecole.trocenchere.utils;

public class SQL_REQUESTS_Utils {

	public static final String SQL_SELECT_USER_BY_USER = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE pseudo=?;";
	public static final String SQL_INSERT_USER = "INSERT INTO utilisateurs(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	public static final String SQL_SELECT_USER_BY_EMAIL = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE email=?;";

	public static final String SQL_UPDATE_USER = "UPDATE utilisateurs SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=? WHERE no_utilisateur=?";

	public static final String SQL_SELECT_ALL_EC_ARTICLES = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE a.etat_vente = 'EC'";

	public static final String SQL_SELECT_EC_ARTICLES_BY_KEYWORD = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE a.etat_vente = 'EC' AND nom_article LIKE CONCAT( '%',?,'%');";

	public static final String SQL_SELECT_EC_ARTICLES_BY_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE a.etat_vente = 'EC' AND no_categorie = ?;";

	public static final String SQL_SELECT_EC_ARTICLES_BY_KEYWORD_AND_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u\r\n" + "ON a.no_utilisateur = u.no_utilisateur\r\n"
			+ "WHERE a.etat_vente = 'EC' AND no_categorie = ? AND nom_article LIKE CONCAT( '%',?,'%');";

	public static final String SQL_SELECT_USER_BIDS_ARTICLES = "SELECT * FROM articles_vendus as a INNER JOIN ENCHERES as e ON a.no_article = e.no_article INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE a.etat_vente = 'EC' AND e.no_utilisateur = ?;";

	public static final String SQL_SELECT_USER_BIDS_ARTICLES_BY_KEYWORD = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE a.etat_vente = 'EC' AND e.no_utilisateur = ? AND a.nom_article LIKE CONCAT( '%',?,'%');";

	public static final String SQL_SELECT_USER_BIDS_ARTICLES_BY_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE a.etat_vente = 'EC' AND e.no_utilisateur = ? AND a.no_categorie = ?;";

	public static final String SQL_SELECT_USER_BIDS_ARTICLES_BY_KEYWORD_AND_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE a.etat_vente = 'EC' AND e.no_utilisateur = ? AND a.no_categorie = ? AND a.nom_article LIKE CONCAT( '%',?,'%');";

	public static final String SQL_SELECT_WON_BIDS_ARTICLES = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE (a.etat_vente = 'VD' OR a.etat_vente = 'RT') AND e.no_utilisateur = ?;";

	public static final String SQL_SELECT_WON_BIDS_ARTICLES_BY_KEYWORD = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE (a.etat_vente = 'VD' OR a.etat_vente = 'RT') AND e.no_utilisateur = ? AND a.nom_article LIKE CONCAT( '%',?,'%');";

	public static final String SQL_SELECT_WON_BIDS_ARTICLES_BY_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE (a.etat_vente = 'VD' OR a.etat_vente = 'RT') AND e.no_utilisateur = ? AND a.no_categorie = ?;";

	public static final String SQL_SELECT_WON_BIDS_ARTICLES_BY_KEYWORD_AND_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN ENCHERES as e\r\n" + "ON a.no_article = e.no_article\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE (a.etat_vente = 'VD' OR a.etat_vente = 'RT') AND e.no_utilisateur = ? AND a.no_categorie = ? AND a.nom_article LIKE CONCAT( '%',?,'%');";

	public static final String SQL_SELECT_USER_SALES = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE u.pseudo = ? AND a.etat_vente = ?";

	public static final String SQL_SELECT_USER_SALES_BY_KEYWORD = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE u.pseudo = ? AND a.etat_vente = ? AND a.nom_article LIKE CONCAT( '%',?,'%')";

	public static final String SQL_SELECT_USER_SALES_BY_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE u.pseudo = ? AND a.etat_vente = ? AND no_categorie = ?";

	public static final String SQL_SELECT_USER_SALES_BY_KEYWORD_AND_CATEGORY = "SELECT * FROM articles_vendus as a\r\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur WHERE u.pseudo = ? AND a.etat_vente = ? AND no_categorie = ? AND a.nom_article LIKE CONCAT( '%',?,'%')";

	public static final String SQL_DISABLE_USER = "UPDATE UTILISATEURS SET ACTIF = 0 WHERE pseudo=?";
	
	public static final String SQL_INSERT_ARTICLE = "INSERT INTO articles_vendus(nom_article, description, date_debut_enchere, "
			+ "date_fin_enchere, prix_initial, no_utilisateur, no_categorie, etat_vente) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String SQL_INSERT_PICKUP= "INSERT INTO retrait(no_article, rue, code_postal, ville) VALUES (?, ?, ?, ?)";
	
	public static final String SQL_DELETE_ARTICLE ="DELETE FROM articles_vendus WHERE no_article = ?";

}
