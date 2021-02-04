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
<title>Connected Home</title>

</head>

<body>
	<header>
		<%@include file="HeaderConnected.jspf" %>
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
		<div class="welcome">
		<c:out value="${sessionScope.user}" />
		est connecté.
		</div>
	
		<h1 class="title">Liste des enchères</h1>
			
		<form action="<%=request.getContextPath()%>/ServletConnectedHome" method="post">
			<div class="checkBoxes">
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
				
					<div class="container2">
						<div>
						
							<label class="selectBoxTitle">
								<input type="radio" name="buyOrSell" value="buy1" onClick="checkBoxes()">
								Achats
							</label>
							<br><br>
							
							<input type="radio" name="buy2" class="checkBox" id="openBids" value="openBids"> 
							<label class="selectBoxLabel">enchères ouvertes</label>
							<br><br>
							
							<input type="radio" name="buy2" class="checkBox" id="myBids" value="myBids"> 
							<label class="selectBoxLabel">mes enchères</label>
							<br><br>
							
							<input type="radio" name="buy2" class="checkBox" id="myWonBids" value="myWonBids"> 
							<label class="selectBoxLabel">mes enchères remportées</label>	
					
						</div>
						<div>
								
							<label class="selectBoxTitle">
								<input type="radio" name="buyOrSell" value="sell1" onClick="checkBoxes()">
								Mes ventes
							</label>
							<br><br>
							
							<input type="radio" name="sell2" class="checkBox" id="currentSales" value="currentSales"> 
							<label class="selectBoxLabel">ventes en cours</label>
							<br><br>
							
							<input type="radio" name="sell2" class="checkBox" id="notStartedSales" value="notStartedSales"> 
							<label class="selectBoxLabel">ventes non débutées</label>
							<br><br>
							
							<input type="radio" name="sell2" class="checkBox" id="endedSales" value="endedSales"> 
							<label class="selectBoxLabel">ventes terminées</label>
		
						</div>
					</div>
				</div>
				<input class="searchButton" type="submit" name="search" value="Rechercher"/>
			</div>
			</form>
			
			<div class="ArticlesDisplay">
				
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
								Fin de l'enchère : ${element.getBidEndDate()} <br>
								Vendeur : ${element.getUser().getUser()} <br>
							</div>
						</div>
					</div>
				</c:forEach>		
			
			</div>
			
		<script type="text/javascript">
	    function checkBoxes() {
	    	console.log('checkBoxes', document.forms[0]);
	    	
	    	if (document.forms[0].buyOrSell[0].checked) {
		    	console.log('achats is checked');
		    	
		    	disableCheckBox("currentSales");
		    	disableCheckBox("notStartedSales");
		    	disableCheckBox("endedSales");  
		    	
		    	enableCheckBox("openBids", true);
		    	enableCheckBox("myBids", false);
		    	enableCheckBox("myWonBids", false);  
	        }
	        else if (document.forms[0].buyOrSell[1].checked) {
		    	console.log('ventes is checked');

		    	disableCheckBox("openBids");
		    	disableCheckBox("myBids");
		    	disableCheckBox("myWonBids");   
		    	
		    	enableCheckBox("currentSales", true);
		    	enableCheckBox("notStartedSales", false);
		    	enableCheckBox("endedSales", false);     
	        }
	    }
	    
	    function disableCheckBox(id) {
	    	var element = document.getElementById(id);
	    	if (element) {
		    	element.checked = false;
	            element.disabled = true;
	    	}
	    }
	    
	    function enableCheckBox(id, checked) {
	    	var element = document.getElementById(id);
	    	if (element) {
		    	element.checked = checked;
	            element.disabled = false;
	    	}
	    }
	    </script>

	</body>

	<footer class="footer" id="footerCell">
			<%@include file="Footer.jspf" %>
	</footer>

</body>
</html>