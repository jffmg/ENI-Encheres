<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.ecole.trocenchere.gestion.erreurs.LecteurMessage" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" href="css/Style.css" type="text/css" />
		<title>Profil</title>
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
	
	<body class="body">
		
		<div class="welcome"><br></div>
	
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
		
		<h1 class="title">Profil</h1>
		
		<div class="divProfile container2">
			
			<p class="labelProfil"> Pseudo : </p>
			<p class= "infoProfil"> ${profile.getUser()}</p>
			
			<p class="labelProfil"> Nom : </p>
			<p class= "infoProfil"> ${profile.getName()}</p>

			<p class="labelProfil"> Prénom : </p>
			<p class= "infoProfil"> ${profile.getFirstName()}</p>
			
			<p class="labelProfil"> Email : </p>
			<p class= "infoProfil"> ${profile.getEmail()}</p>

			<p class="labelProfil"> Téléphone </p>
			<p class= "infoProfil"> ${profile.getPhone()}</p>
			
			<p class="labelProfil"> Rue : </p>
			<p class= "infoProfil"> ${profile.getStreet()}</p>

			<p class="labelProfil"> Code Postale : </p>
			<p class= "infoProfil"> ${profile.getPostCode()}</p>

			<p class="labelProfil"> Code Ville : </p>
			<p class= "infoProfil"> ${profile.getCity()}</p>
		
		</div>

		<c:if test="${profile.getUser()==sessionScope.user}">
			<a href="<%=request.getContextPath()%>/ServletConnectedHome"> <!-- ServletUpdateProfile  -->
				<input type="button" name="modifier" value="Modifier mon profil" class="searchButtonNC"/>
			</a>	
		</c:if>
		
		
	<footer class="footer" id="footerCell">
		<%@include file="Footer.jspf" %>
	</footer>

</body>
</html>