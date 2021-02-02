<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" href="css/Style.css" type="text/css" />
		<title>Inscription</title>
	</head>

	<body>
	
		<header>
			<c:import url="HeaderNC.html"></c:import>
		</header>
	
		<div id="main">

			<div class="mainContent">

				<div class="MainContentTop">
					<form id="idform" action="<%=request.getContextPath()%>/ServletSignUp" method="POST">
						<p>Tous les champs marqués d'un astérisque sont obligatoires.</p>
						<ul>
							<li>
								<label for="userName">Nom d'utilisateur* (caractères alphanumériques seulement)</label>
								<input type="text" pattern="[a-zA-Z0-9]*" name="userName" placeholder="Nom d'utilisateur" autofocus="autofocus">
							</li>
							<li>
								<label for="password">Mot de passe*</label>
								<input type="password" name="password" placeholder="motDePasse">
							</li>
							<li>
								<label for="name">Nom*</label>
								<input type="text" name="name" placeholder="Nom">
							</li>
							<li>
								<label for="firstName">Prénom*</label>
								<input type="text" name="firstName" placeholder="Prénom">
							</li>
							<li>
								<label for="email">Email*</label>
								<input type="email" name="email" placeholder="adresse@exemple.fr">
							</li>
							<li>
								<label for="phone">Téléphone</label>
								<input type="tel" pattern="0[1-9]([0-9]{2}){4}" name="phone" placeholder="0123456789">
							</li>
							<li>
								<label for="street">Adresse*</label>
								<input type="text" name="street" placeholder="1 allée de l'Univers">
							</li>
							<li>
								<label for="postCode">Code postal*</label>
								<input type="text" name="postCode" placeholder="01234">
							</li>
							<li>
								<label for="city">Ville*</label>
								<input type="text" name="city" placeholder="Exempleville">
							</li>
						</ul>

						<div class="nav">
							<input type="submit" name="signUpButton" value="Valider l'inscription" class="validationButton" />
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