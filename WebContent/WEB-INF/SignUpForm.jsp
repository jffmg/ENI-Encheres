<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.ecole.trocenchere.gestion.erreurs.LecteurMessage" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" href="css/Style.css" type="text/css" />
		<title>Inscription</title>
	</head>

	<body>
	
		<header class="header">
			<c:import url="HeaderNC.html"></c:import>
		</header>
		
		<c:if test="${!empty listeCodesErreur}">
			<div class="displayLine" role="alert">
			 <!--   <strong class="messageError">Erreur!</strong> -->
			  <br>
			  <ul>
			  	<c:forEach var="code" items="${listeCodesErreur}">
			  		<li class="messageError">${LecteurMessage.getMessageErreur(code)}</li>
			  	</c:forEach>
			  </ul>
			</div>
		</c:if>
	
		<div id="mainSignUp">

			<div class="mainContent">

				<div class="MainContentTop">
					<form id="idform" action="<%=request.getContextPath()%>/ServletSignUp" method="POST">
						<p class="marginBottomTwoEm">Tous les champs marqués d'un astérisque sont obligatoires.</p>
						<ul>
							<li>
								<label class="labelSignForm" for="userName">Nom d'utilisateur* : </label>
								<input type="text" pattern="[a-zA-Z0-9]*" name="userName" placeholder="Nom d'utilisateur (caractères alphanumériques seulement)" autofocus="autofocus">
							</li>
							<li>
								<label class="labelSignForm" for="password">Mot de passe* : </label>
								<input type="password" name="password" placeholder="Mot de passe">
							</li>
							<li>
								<label class="labelSignForm" for="name">Nom* : </label>
								<input type="text" name="name" placeholder="Nom">
							</li>
							<li>
								<label class="labelSignForm" for="firstName">Prénom* : </label>
								<input type="text" name="firstName" placeholder="Prénom">
							</li>
							<li>
								<label class="labelSignForm" for="email">Email* : </label>
								<input type="email" name="email" placeholder="adresse@exemple.fr">
							</li>
							<li>
								<label class="labelSignForm" for="phone">Téléphone : </label>
								<input type="tel" pattern="0[1-9]([0-9]{2}){4}" name="phone" placeholder="0123456789">
							</li>
							<li>
								<label class="labelSignForm" for="street">Adresse* : </label>
								<input type="text" name="street" placeholder="1 allée de l'Univers">
							</li>
							<li>
								<label class="labelSignForm" for="postCode">Code postal* : </label>
								<input type="text" name="postCode" placeholder="01234">
							</li>
							<li>
								<label class="labelSignForm" for="city">Ville* : </label>
								<input type="text" name="city" placeholder="Ville">
							</li>
						</ul>

						<div class="navSignForm">
							<input type="submit" name="signUpButton" value="Valider l'inscription" class="signUpButton" />
						</div>
					</form>
				</div>
			</div>
		</div>

		<footer id="footerCell">
			<c:import url="Footer.html"></c:import>
		</footer>

	</body>
</html>