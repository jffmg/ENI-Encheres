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
       		<c:if test="${message!=null}">
				<p style="color:green">${message}</p>
			</c:if>

			<h1 class="title">Vente</h1>
			
			<div class="divImg">	
			<img  class="articleImg" alt="Object" src="<%=request.getContextPath()%>/images/object_to_sale.jpg">
			</div>

			<div class="divProfile container2">

				
				
				<p class="labelProfil">Nom :</p>
				<p class= "infoProfil">${currentArticle.getName()}</p>
				
				<p class="labelProfil">Description :</p>
				<p class= "infoProfil">${currentArticle.getDescription()}</p>
				
				<p class="labelProfil">Catégorie :</p>
				<p class= "infoProfil">${currentArticle.getCategory().getCategoryLabel()}</p>
				
				<!-- 	<label class="labelProfil">Catégorie :</label>
					<select name="categories" id="category">
						<c:forEach var="cat" items="${categories}">
							<option value="${cat}">${cat}</option>
						</c:forEach>
					</select> -->
					
				<p class="labelProfil">Meilleure offre :</p>
				<p class= "infoProfil">${currentArticle.getSalePrice()} point(s)</p>
				
				<p class="labelProfil">Mise à prix :</p>
				<p class= "infoProfil">${currentArticle.getStartingBid()} point(s)</p>
				
				<p class= "labelProfil"> Fin de l'enchère :</p>
				<p class= "infoProfil">${endDateString}</p>
				
				<p class="labelProfil">Vendeur : </p>
				<p class= "infoProfil">${currentArticle.getUser().getUser()}</p>
			</div>
			<form action="<%=request.getContextPath()%>/Bid?startingBid=${currentArticle.getStartingBid()}&articleID=${currentArticle.getIdArticle()}&currentOffer=${currentArticle.getSalePrice()}&profile=${sessionScope.user}" method="post">
				<c:if test="${!currentArticle.getUser().getUser().equals(sessionScope.user)}">
					<div class="container3">
						<label for="myOffer">Mon offre :</label>
						<input style="height:2em" type="number" name="myOffer" step="1" min="${currentArticle.getSalePrice()+1}" value="${currentArticle.getSalePrice()}"> point(s)
						<input type="submit" id="submit" name="validate" value="Enchérir" class="profileButton" />
					</div>
				</c:if>
			</form>
			
			<form action="<%=request.getContextPath()%>/Bid?startingBid=${currentArticle.getStartingBid()}&articleID=${currentArticle.getIdArticle()}&currentOffer=${currentArticle.getSalePrice()}&profile=${sessionScope.user}" method="post">
				<c:if test="${currentArticle.getUser().getUser().equals(sessionScope.user) && hasAuctionStarted == false}">
					<div class="container3">
						<input type="submit" id="submit" name="validate" value="Modifier ma vente" class="profileButton" />
					</div>
				</c:if>
			</form>

			<footer class="footer" id="footerCell">
				<%@include file="Footer.jspf"%>
			</footer>
		</div>
	</body>
</html>