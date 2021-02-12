<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.ecole.trocenchere.gestion.erreurs.LecteurMessage" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ClassStyle.css" type="text/css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ButtonStyle.css" type="text/css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/GeneralStyle.css" type="text/css" />
		<title>Inscription</title>
	</head>

	<body class="body">
	
		<header class="header">
			<%@include file="HeaderNC.jspf" %>
		</header>
		
		<%@include file="Errors.jspf" %>
	
		<div id="mainSignUp">

			<div class="mainContent">

				<div class="MainContentTop">
					<form id="idform" action="<%=request.getContextPath()%>/SignUp" method="POST">
						<p class="marginBottomTwoEm">Tous les champs marqués d'un astérisque sont obligatoires.</p>
						<ul>
							<li>
								<label class="labelSignForm" for="userName">Nom d'utilisateur* : </label>
								<input class="inputText" type="text" pattern="[a-zA-Z0-9]*" name="userName" placeholder="Nom d'utilisateur (caractères alphanumériques seulement)" autofocus="autofocus" required="required">
							</li>
							<li>
								<label class="labelSignForm" for="password">Mot de passe* : </label>
								<input class="inputText" type="password" name="password" placeholder="Mot de passe" required="required">
							</li>
							<li>
								<label class="labelSignForm" for="name">Nom* : </label>
								<input class="inputText" type="text" name="name" placeholder="Nom" required="required">
							</li>
							<li>
								<label class="labelSignForm" for="firstName">Prénom* : </label>
								<input class="inputText" type="text" name="firstName" placeholder="Prénom" required="required">
							</li>
							<li>
								<label class="labelSignForm" for="email">Email* : </label>
								<input class="inputText" type="email" name="email" placeholder="adresse@exemple.fr" required="required">
							</li>
							<li>
								<label class="labelSignForm" for="phone">Téléphone* : </label>
								<input class="inputText" type="tel" name="phone" pattern="|0[1-9]([0-9]{2}){4}" placeholder="0123456789">
							</li>
							<li>
								<label class="labelSignForm" for="street">Adresse* : </label>
								<input class="inputText" type="text" name="street" placeholder="1 allée de l'Univers" required="required">
							</li>
							<li>
								<label class="labelSignForm" for="postCode">Code postal* : </label>
								<input class="inputText" type="text" name="postCode" placeholder="01234" required="required">
							</li>
							<li>
								<label class="labelSignForm" for="city">Ville* : </label>
								<input class="inputText" type="text" name="city" placeholder="Ville" required="required">
							</li>
						</ul>

						<div class="navSignForm">
							<input type="submit" name="signUpButton" value="Valider l'inscription" class="signUpButton" />
						</div>
					</form>
				</div>
			</div>
		</div>

		<footer class="footer" id="footerCell">
			<%@include file="Footer.jspf" %>
		</footer>

	</body>
</html>