<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="fr.eni.ecole.trocenchere.gestion.erreurs.LecteurMessage" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="css/ClassStyle.css" type="text/css" />
	<link rel="stylesheet" href="css/ButtonStyle.css" type="text/css" />
	<link rel="stylesheet" href="css/GeneralStyle.css" type="text/css" />
<title>ENI-Enchères - Accueil</title>

</head>

<body>
	<header>
		<%@include file="HeaderDisconnected.jspf" %>
	</header>
		
		<div class="welcome"><br></div>
			
		<%@include file="Errors.jspf" %>
		
		<h1 class="title">Liste des enchères</h1>
			
		<form action="<%=request.getContextPath()%>/ServletNonConnectedHome" method="post">
		<div class="checkBoxesNC">
			<div class="container">
			
				<div class="lookFor">
					<label class="lookForLabel">Filtre : </label>
					<input class="inputSelect" type="text" name="keyWord" placeholder="Le nom de l'article contient..." />
				
					<label class="lookForLabel">Catégories : </label> 
					<select name="categories" id="category">
						<c:forEach var="cat" items="${categories}">
							<option value="${cat}">${cat}</option>
						</c:forEach>
					</select> 
				</div>
			
			</div>
				<input class="searchButtonNC" type="submit" name="search" value="Rechercher"/>
			</div>
			</form>
			
			<%@include file="ArticlesDisplay.jspf" %>

	</body>

	<footer class="footer" id="footerCell">
			<%@include file="Footer.jspf" %>
	</footer>

</body>
</html>