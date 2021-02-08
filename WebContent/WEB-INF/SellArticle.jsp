<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="fr.eni.ecole.trocenchere.gestion.erreurs.LecteurMessage"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" href="css/ClassStyle.css" type="text/css" />
<link rel="stylesheet" href="css/ButtonStyle.css" type="text/css" />
<link rel="stylesheet" href="css/GeneralStyle.css" type="text/css" />
<title>Vente</title>
</head>

<body>
	<header>
		<c:choose>
			<c:when test="${sessionScope.user!=null}">
				<%@include file="HeaderConnected.jspf"%>
			</c:when>
			<c:otherwise>
				<%@include file="HeaderDisconnected.jspf"%>
			</c:otherwise>
		</c:choose>

	</header>
<body class="body">

	<div class="welcome">
		<br>
	</div>

	<%@include file="Errors.jspf"%>

	<h1 class="title">Nouvelle vente</h1>

	<form
		action="<%=request.getContextPath()%>/ServletSellArticle?profile=${sessionScope.user}"
		method="POST">

		<div class="divProfile container2">

			<p class="labelProfil">Article :</p>
			<p>
				<input class="inputText" type="text" name="name" size=35 />
			<p>
			<p class="labelProfil">Description :</p>
			<p>
				<textarea name="description" class="inputText" cols=35 rows=5 /></textarea>
			<p>
			<p class="labelProfil">Catégorie :</p>
			<p>
				<label class="lookForLabel">Catégories : </label> <select
					name="categories" id="category">
					<c:forEach var="cat" items="${categories}">
						<option value="${cat}">${cat}</option>
					</c:forEach>
				</select>
			<p>

				<c:if test="${profile.getUser().equals(sessionScope.user)}">
					<div class="container3">
						<input type="submit" id="submit" name="validate" value="Valider"
							class="profileButton" /> <a
							href="<%=request.getContextPath()%>/ServletProfile?profile=${sessionScope.user}">
							<input type="button" name="cancel" value="Annuler"
							class="profileButton" />
						</a>
					</div>
				</c:if>
	</form>





	<footer class="footer" id="footerCell">
		<%@include file="Footer.jspf"%>
	</footer>

</body>
</html>