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
<title>ENI-Enchères - Vendre un article</title>
</head>

<body>
	<header>
		<%@include file="HeaderConnected.jspf"%>
	</header>

	<%@include file="Errors.jspf"%>

	<h1 class="title">Nouvelle vente</h1>

	<form
		action="<%=request.getContextPath()%>/ServletSellArticle?profile=${sessionScope.profile}"
		method="POST">

		<div class="divProfile container2">

			<label for="name" class="labelProfil">Article :</label>
			<p>
				<input class="inputText" type="text" name="name" size=35 />
			<p>
			<p class="labelProfil">Description :</p>
			<p>
				<textarea name="description" class="inputText" cols=35 rows=5 /></textarea>
			<p>
			<p class="labelProfil">Catégorie :</p>
			<select
					name="categories" id="category">
					<c:forEach var="cat" items="${categories}">
						<option value="${cat}">${cat}</option>
					</c:forEach>
				</select>
<!-- 			<p> -->
<!-- 			<input type="file" name="articlePicture" id="articlePicture"> -->
<!--   <input type="submit"> -->
<!-- 				</p> -->
			<p>
			<label for="startBid" class="labelProfil">Mise à prix :</label>
			<input type="number" name="startBid" size="6" value="50"> points
			</p>
			<p>
			<label for="startDate" class="labelProfil">Début de l'enchère (jour et heure)</label>
			<input type="datetime-local" name="startDate">
			</p>
			<p>
			<label for="endDate" class="labelProfil">Fin de l'enchère (jour et heure)</label>
			<input type="datetime-local" name="endDate">
			</p>
			<fieldset>
			<legend>Retrait</legend>
			<label for="street" class="labelProfil">Rue :</label>
			<p>
				<input class="inputText" type="text" name="street" size="50" value="${profile.getStreet()}"/>
			<p>
			<label for="postCode" class="labelProfil">Code postal :</label>
			<p>
				<input class="inputText" type="text" name="postCode" size=6 value="${profile.getPostCode()}"/>
			<p>
			<label for="city" class="labelProfil">Ville :</label>
			<p>
				<input class="inputText" type="text" name="city" size=50 value="${profile.getCity()}"/>
			<p>
			</fieldset>


				<c:if test="${profile.getUser().equals(sessionScope.profile)}">
					<div class="container3">
						<input type="submit" id="submit" name="validate" value="Valider"
							class="profileButton" /> <a
							href="<%=request.getContextPath()%>/ServletConnectedHome">
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