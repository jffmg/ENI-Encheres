<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="fr.eni.ecole.trocenchere.gestion.erreurs.LecteurMessage" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="css/ClassStyle.css" type="text/css" />
	<link rel="stylesheet" href="css/ButtonStyle.css" type="text/css" />
	<link rel="stylesheet" href="css/GeneralStyle.css" type="text/css" />
<title>Connexion</title>
</head>

<body class="body">

	<header class="header">
		<%@include file="HeaderNC.jspf" %>
	</header>

	<%@include file="Errors.jspf" %>

	<div id="main">

		<div class="mainContent">

			<div class="MainContentTop">

				<form id="idForm" action="${pageContext.request.contextPath}/ServletConnection" method="POST">
					<input class="inputText" type="hidden" name="submitted" value="true" />

					<ul class="connectionParameters">

						<!-- check rememberMe value -->
						<% String userName =null;
							if (request.getAttribute("userNameRemembered") != null) {
								userName = String.valueOf(request.getAttribute("userNameRemembered"));
							}
						%>
						<li><label name="userName" for="userId" class="idLabel">Identifiant :</label> 
							<input class="inputText" type="text" name="user" class="idField" value="<%=(userName != null) ? userName : ""%>" required/> 
							<br>
						</li>

						<li><label for="password" class="idLabel">Mot de passe :</label> 
							<input class="inputText" type="password" name="password" class="passwordField" required/>
							<br>
						</li>
					</ul>

					<div class="nav">
						<div>
							<input type="submit" name="connectionButton" value="Connexion"
								class="validationButton" />
						</div>

						<div class="passwordActions">
							<input type="checkbox" name="rememberMe" class="remember"
								value="false" /> <label>Se souvenir de moi</label> <br> <a
								class="link" href="mailto:${param.email}">Mot de passe
								oublié</a>
						</div>
					</div>

				</form>


			</div>

			<div class="MainContentBottom">
				<form action="<%=request.getContextPath()%>/ServletSignUp" method="GET">
					<input type="submit" name="createAccountButton"
						value="Créer un compte" class="createAccountButton" />
				</form>
			</div>
		</div>

	</div>

	<footer class="footer" id="footerCell">

		<%@include file="Footer.jspf" %>

	</footer>

</body>

</html>