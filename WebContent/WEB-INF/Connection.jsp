<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" href="css/Style.css" type="text/css" />
<title>Connexion</title>

<c:if test="${param.submitted}">
	<c:if test="${empty param.user}" var="noId" />
	<c:if test="${empty param.password}" var="noPassword" />
</c:if>

	<c:if test="${not (noId or noPassword)}">
		<c:set value="${param.id}" var="user" scope="request" />
		<c:set value="${param.password}" var="password" scope="request" />
		<jsp:forward page="${pageContext.request.contextPath}/Connection" />

	</c:if>

</head>

<body>

	<header id="header">
		<c:import url="HeaderNC.html" />
	</header>

	<div id="main">

		<div class="mainContent">

			<div class="MainContentTop">

				<form id="idForm" action="" method="POST">
					<input type="hidden" name="submitted" value="true" />

					<ul class="connectionParameters">

							<li><label for="userId" class="idLabel">Identifiant</label> 
							<input
								type="text" name="user" class="idField"
								value="<c:out value="${param.user}"/>" /> 
							<br> 
							
							<c:if test="${noId}">
								<small><font color="red"> Veuillez entrer un
											identifiant </font></small>
							</c:if>
						</li>

						<li class="pwLi"><label for="password" class="idLabel">Mot de
								passe</label> <input type="password" name="password">
						<li><label for="password" class="idLabel">Mot de
								passe</label> 
							<input type="password" name="password"
							class="passwordField" value="<c:out value="${param.password}"/>" /><br>
							
							<c:if test="${noPassword}">
								<small><font color="red"> Veuillez entrer un mot
										de passe </font></small>
							</c:if>
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
								class="link"
								href="mailto:${param.email}">Mot
								de passe oublié</a>
						</div>
					</div>

				</form>


			</div>

			<div class="MainContentBottom">
				<form action="ConnectedHome.jsp" method="GET">
					<input type="submit" name="createAccountButton"
						value="Créer un compte" class="createAccountButton" />
				</form>
			</div>
		</div>

	</div>

	<footer id="footer">
			<c:import url="Footer.html" />
	</footer>

</body>

</html>