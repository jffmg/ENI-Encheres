<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="fr.eni.ecole.trocenchere.gestion.erreurs.LecteurMessage" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/Style.css" type="text/css" />
<title>Disconnected Home</title>

</head>

<body>
	<header>
		<%@include file="HeaderDisconnected.jspf" %>
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
	
	<body class="body">
		
		<div class="welcome"><br></div>
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
			
			<div class="ArticlesDisplay">
				<div class="divArticle">
					<div class="articleName">
						Article Test en dur dans Html
					</div>
					<div class="container2">
						<img  class="articleImg" alt="Object" src="images/objet_à_vendre.jpg">
						<div class="articleInfo">
							Prix : 0 point(s) <br>
							Meilleure enchère : 0 point(s)<br>
							Fin de l'enchère : dd-MM-yyyy HH:mm <br>
							Vendeur : xxxxxxxxx <br>
						</div>
					
					</div>
				</div>
				
				<div class="divArticle">
					<div class="articleName">
						Article Test en dur dans Html
					</div>
					<div class="container2">
						<img  class="articleImg" alt="Object" src="images/objet_à_vendre.jpg">
						<div class="articleInfo">
							Prix : 0 point(s) <br>
							Meilleure enchère : 0 point(s)<br>
							Fin de l'enchère : dd-MM-yyyy HH:mm <br>
							Vendeur : xxxxxxxxx <br>
						</div>
					
					</div>
				</div>
				
				<c:forEach var="element" items="${articlesSelected}">
					<div class="divArticle">
						<div class="articleName">
							${element.getName()}
						</div>
						<div class="container2">
							<img  class="articleImg" alt="Object" src="images/objet_à_vendre.jpg">
							<div class="articleInfo">
								Prix : ${element.getSalePrice()} point(s) <br>
								Meilleure enchère : 0 point(s)<br>
								Fin de l'enchère : ${element.getStartingBid()} <br>
								Vendeur : ${element.getIdUser()} <br>
							</div>
						</div>
					</div>
				</c:forEach>
			
			
			</div>

	</body>

	<footer class="footer" id="footerCell">
			<%@include file="Footer.jspf" %>
	</footer>

</body>
</html>