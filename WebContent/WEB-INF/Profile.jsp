<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.ecole.trocenchere.gestion.erreurs.LecteurMessage" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" href="css/ClassStyle.css" type="text/css" />
		<link rel="stylesheet" href="css/ButtonStyle.css" type="text/css" />
		<link rel="stylesheet" href="css/GeneralStyle.css" type="text/css" />
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
	
		<%@include file="Errors.jspf" %>
		
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

		<c:if test="${profile.getUser().equals(sessionScope.user)}">
			<div class="container3">
				<a href="<%=request.getContextPath()%>/ServletUpdateProfile?profile=${sessionScope.user}">
					<input type="button" name="modify" value="Modifier mon profil" class="profileButton"/>
				</a>
				
				<a onclick="showPopUp();">
					<input type="button" name="delete" value="Supprimer mon profil" class="profileButton"/>
				</a>	
			</div>
		</c:if>
		
		<div id="confirmationPopUp" class="confirmationPopUp">
			<p class="textConfirmationPopUp"> Etes vous sûre de vouloir supprimer votre profil ?</p>
			<a onclick="hidePopUp();">
				<input type="button" name="cancel" value="Annuler" class="buttonPopUp"/>
			</a>
				
			<a href="<%=request.getContextPath()%>/ServletDisableProfile">
				<input type="button" name="validate" value="Supprimer mon profil" class="buttonPopUp"/>
			</a>		
		</div>
		
		<script type="text/javascript">
			function hidePopUp(){
				document.getElementById("confirmationPopUp").style.display = 'none';
			}
			
			function showPopUp(){
				document.getElementById("confirmationPopUp").style.display = 'block';
			}
		</script>
		
		
	<footer class="footer" id="footerCell">
		<%@include file="Footer.jspf" %>
	</footer>

</body>
</html>