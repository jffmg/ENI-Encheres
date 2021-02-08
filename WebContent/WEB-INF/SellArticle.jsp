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
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ClassStyle.css" type="text/css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ButtonStyle.css" type="text/css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/GeneralStyle.css" type="text/css" />
	<title>ENI-Enchères - Vendre un article</title>
</head>
	
<body>
	<header>
		<%@include file="HeaderConnected.jspf"%>
	</header>
	
	<div class="body">

		<div class="welcome">
		</div>
	
		<%@include file="Errors.jspf"%>
		<c:if test="${message!=null}">
			<p style="color:green">${message}</p>
		</c:if>
	

	<h1 class="title">Nouvelle vente</h1>

	<form
		action="<%=request.getContextPath()%>/Connected/SellArticle?profile=${sessionScope.user}"
		method="POST">

		<div class="divProfile container2">

			<p class="labelSellArticleInfo">
				<label for="name">Article :</label>
				<input class="inputText" type="text" name="name" autofocus="autofocus"  required="required"/>
			</p>
			
			<p class="labelSellArticleInfo">
				<label>Catégorie :</label>
				<select
					name="categories" id="category" required="required">
					<c:forEach var="cat" items="${categories}">
						<option value="${cat}">${cat}</option>
					</c:forEach>
				</select>
			</p>
			<br>
			
			<p class="labelSellArticleInfo">
				<label for="description">Description :</label>
				<textarea name="description" class="inputText" rows=5 required="required"/></textarea>
			</p>

<!-- 			<p> -->
<!-- 			<label for="articlePicture">Photo de l'article</label> -->
<!-- 			<input type="file" name="articlePicture" id="articlePicture accept="image/png, image/jpeg""> -->
<!--   <input type="submit"> -->
<!-- 				</p> -->
			
			<p class="labelSellArticleInfo">
				<label for="startBid">Mise à prix :</label>
				<input type="number" name="startBid" value="50" class="inputText"> points
			</p>
			
			<p  class="labelSellArticleInfo">
				<label for="startDate">Début de l'enchère (jour et heure)</label>
				<input type="datetime-local" name="startDate" required="required">
			</p>
			
			<p class="labelSellArticleInfo">
				<label for="endDate">Fin de l'enchère (jour et heure)</label>
				<input type="datetime-local" name="endDate" required="required">
			</p>
			
			<fieldset class="pickUpInfo container2">
				<legend>Retrait</legend>

				<p class="labelPickUpinfo">
					<label for="street">Rue :</label>
				</p>
				<input class="inputPickUpinfo" type="text" name="street" value="${profile.getStreet()}"/><br>

				<p class="labelPickUpinfo">
					<label for="postCode">Code postal :</label>
				</p>
				<input class="inputPickUpinfo" type="text" name="postCode" value="${profile.getPostCode()}"/><br>

				<p class="labelPickUpinfo">
					<label for="city">Ville :</label>		
				</p>
				<input class="inputPickUpinfo" type="text" name="city" value="${profile.getCity()}"/><br>
			</fieldset>
			
		</div>

		<div>
			<input type="submit" name="sellArticleButton" value="Mettre en vente" class="searchButton" />
		</div>
				
	</form>

	<footer class="footer" id="footerCell">
		<%@include file="Footer.jspf"%>
	</footer>

</div>
</body>
</html>