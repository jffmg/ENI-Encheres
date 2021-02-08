<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="fr.eni.ecole.trocenchere.gestion.erreurs.LecteurMessage"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ClassStyle.css" type="text/css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ButtonStyle.css" type="text/css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/GeneralStyle.css" type="text/css" />
		<title>Enchérir</title>
	</head>

	<body>
    	<header>
        	<c:choose>
            	<c:when test="${sessionScope.user!=null}">
                	<%@include file="HeaderConnected.jspf" %>
            	</c:when> 
         		<c:otherwise >
                	<%@include file="HeaderDisconnected.jspf" %>
            	</c:otherwise>
        	</c:choose>
	    </header>

    	<div class="body">
		
		
        	<div class="welcome"><br></div>

       		<%@include file="Errors.jspf" %>

			<h1 class="title">Nouvelle vente</h1>

			<div class="divProfile container2">

				<img  class="articleImg" alt="Object" src="<%=request.getContextPath()%>/images/object_to_sale.jpg">
				<p>${currentArticle.getName()}</p>
				<p class="labelProfil">Description :</p>
				<p>${currentArticle.getDescription()}</p>
				<p class="labelProfil">Catégorie</p>
				<!-- 	<label class="labelProfil">Catégorie :</label>
					<select name="categories" id="category">
						<c:forEach var="cat" items="${categories}">
							<option value="${cat}">${cat}</option>
						</c:forEach>
					</select> -->
				<p>${currentArticle.getIdCategory()}</p>
				<p>Meilleure offre : </p>
				<p class="labelProfil">Mise à prix : ${currentArticle.getStartingBid()} points</p>
				<p> Fin de l'enchère : ${currentArticle.getBidEndDate()}</p>
				<p> Retrait </p>
				<p>Vendeur : ${currentArticle.getUser().getUser()}</p>
			</div>

			<form action="<%=request.getContextPath()%>/Connected/Bid" method="post">
				<c:if test="${profile.getUser().equals(sessionScope.profile)}">
					<div class="container3">
						<label for="bid">Mon offre :</label>
						<input type="number" name="bid" value="${article.getCurrentBid()}">
						<input type="submit" id="submit" name="validate" value="Enchérir" class="profileButton" />
					</div>
				</c:if>
			</form>

			<footer class="footer" id="footerCell">
				<%@include file="Footer.jspf"%>
			</footer>
		</div>
	</body>
</html>