<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<title>ENI-Enchères - Accueil (${sessionScope.user})</title>

</head>

<body>
	<header>
		<%@include file="HeaderConnected.jspf"%>
	</header>
	
	<div class="body">

		<div class="welcome">
			<c:out value="${sessionScope.user}" />
			est connecté(e)
		</div>
	
		<%@include file="Errors.jspf"%>
	
		<h1 class="title">Liste des enchères</h1>
	
		<form action="<%=request.getContextPath()%>/Connected/Home"
			method="post">
			<div class="checkBoxes">
				<div class="container">
	
					<div class="lookFor">
						<label class="lookForLabel">Filtre : </label> <input
							class="inputSelect" type="text" name="keyWord"
							placeholder="Le nom de l'article contient..." /> <label
							class="lookForLabel">Catégories : </label> <select
							name="categories" id="category">
							<c:forEach var="cat" items="${categories}">
								<option value="${cat}">${cat}</option>
							</c:forEach>
						</select>
					</div>
	
					<div class="container2">
						<div>
	
							<label class="selectBoxTitle"> 
							
							<input type="radio"
								name="buyOrSell" id="buy1" value="buy1" onClick="checkBoxes()">
								Mes Achats
							</label> <br> <br>
							 
							<input type="radio" name="buy2"
								class="checkBox" id="openBids" value="openBids">
							<label class="selectBoxLabel">enchères ouvertes
							</label> <br><br> 
							
							<input type="radio" name="buy2" class="checkBox"
								id="myBids" value="myBids"> 
							<label class="selectBoxLabel">mes enchères
							</label> <br> <br> 
							
							<input type="radio" name="buy2"
								class="checkBox" id="myWonBids" value="myWonBids"> 
							<label class="selectBoxLabel">enchères remportées
							</label>
	
						</div>
	
						<div>
	
							<label class="selectBoxTitle"> 
							
							<input type="radio"
								name="buyOrSell" value="sell1" onClick="checkBoxes()">
								Mes ventes
							</label> <br> <br> 
							
							<input type="radio" name="sell2"
								class="checkBox" id="currentSales" value="currentSales">
							<label class="selectBoxLabel">ventes en cours
							</label> <br> <br>
							
							<input type="radio" name="sell2" class="checkBox"
								id="notStartedSales" value="notStartedSales"> 
							<label
								class="selectBoxLabel">ventes non débutées
							</label> <br> <br>
							
							<input type="radio" name="sell2" class="checkBox" id="endedSales"
								value="endedSales"> 
							<label class="selectBoxLabel">ventes terminées
							</label>
	
						</div>
					</div>
				</div>
				<input class="searchButton" type="submit" name="search"
					value="Rechercher" />
			</div>
		</form>
	
	
		<%@include file="ArticlesDisplay.jspf"%>
	
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
	
		<script>
			window.addEventListener('load', function () {
				enableCheckBox('buy1', true); 
		    	
		    	enableCheckBox("openBids", true);
		    	enableCheckBox("myBids", false);
		    	enableCheckBox("myWonBids", false); 
		    	
				disableCheckBox("currentSales");
		    	disableCheckBox("notStartedSales");
		    	disableCheckBox("endedSales");
			});
		</script>
					
		<footer class="footer" id="footerCell">
			<%@include file="Footer.jspf"%>
		</footer>
</div>
</body>