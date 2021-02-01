<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/AccueilNonConnecte.css"
	type="text/css" />
<title>Accueil</title>
</head>
<body>

	<div id="login">
		<h1><img width="250" src="images/logo.png" alt="logo"/></h1>
		


		<a class="link" href="${pageContext.request.contextPath}/Connexion">S'inscrire
			- Se connecter</a>
	</div>

	<h2>Liste des Enchères</h2>

	<form action="${pageContext.request.contextPath}/AccueilNonConnecte"
		method="POST">
		
		<div id="filtres">

			<div>
				<label>Filtres : </label>
			</div>
			
			<div>
				<input class="search" type="search"
					placeholder="Le nom de l'article contient..." id="search" size="30"
					multiple>
				<button type="button" class="search-button">
					<img src="images/loupe.png"> 
				</button>
			</div>
			
			<div>
				<label>Catégories : </label> <select name="Categories" id="category">
					<option value="Toutes">Toutes</option>
					<option value="Informatique">Informatique</option>
					<option value="Ameublement">Ameublement</option>
					<option value="Vêtement">Vêtement</option>
					<option value="SportLoisirs">Sport & Loisirs</option>
				</select> 
				<input type="submit" name="delete" value="Rechercher" />
			</div>


		</div>
		
	</form>

</body>
</html>