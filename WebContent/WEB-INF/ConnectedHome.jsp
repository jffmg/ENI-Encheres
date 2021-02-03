<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="fr.eni.ecole.trocenchere.gestion.erreurs.LecteurMessage" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connected Home</title>
<link rel="stylesheet" href="css/Style.css" type="text/css" />
</head>

<body>
	<header>		
		<%@include file="HeaderConnected.jspf" %>
		<h1>Liste des enchères</h1>
	</header>

	<p>
		<c:out value="${sessionScope.user}" />
		est connecté
	</p>

	<div id="main">

		<div>

			<div>
				<input type="text" name="articleContains" placeholder="Le nom de l'article contient..." />
				
				<label>Catégories : </label> 
				<select name="Categories" id="category">
					<option value="Toutes">${initParam.ALL}</option>
					<option value="Informatique">${initParam.COMPUTER}</option>
					<option value="Ameublement">${initParam.FURNISHINGS}</option>
					<option value="Vêtement">${initParam.CLOTHING}</option>
					<option value="SportLoisirs">${initParam.SPORT}</option>
				</select> 
				
			</div>

			<div class="checkBoxes"></div>
			<ul>
			<label>Achats</label>
			<li><input type="checkbox" name="openBids" class="checkBox"
								value="false" /> <label>enchères ouvertes</label></li>
			<li><input type="checkbox" name="myBids" class="checkBox"
								value="false" /> <label>mes enchères</label></li>
			<li><input type="checkbox" name="myWonBids" class="checkBox"
								value="false" /> <label>mes enchères remportées</label></li>
			</ul>
			
			<ul>
			<label>Mes ventes</label>
			<li><input type="checkbox" name="currentSales" class="checkBox"
								value="false" /> <label>ventes en cours</label></li>
			<li><input type="checkbox" name="notStartedSales" class="checkBox"
								value="false" /> <label>ventes non débutées</label></li>
			<li><input type="checkbox" name="endedSales" class="checkBox"
								value="false" /> <label>ventes terminées</label></li>
			</ul>

		</div>

		<div class="searchButton"><input type="submit" name="search" value="Rechercher"/></div>

		<div class="ArticlesDisplay"></div>

	</div>




















	<footer id="footerCell">
		<c:import url="Footer.html"></c:import>
	</footer>

</body>
</html>