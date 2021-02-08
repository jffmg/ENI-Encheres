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
		<title>Modifier le profil</title>
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
		
		<h1 class="title"> Modifier votre profil</h1>
		
		<form action="<%=request.getContextPath()%>/ServletUpdateProfile?profile=${sessionScope.user}" method="POST">
		
		<div class="divProfile container2">
			
			<p class="labelProfil"> Pseudo : </p>
			<p><input class="inputText" type="text" name="userName" value="${profile.getUser()}" size=35/> <p>
			
			<p class="labelProfil"> Nom : </p>
			<p><input class="inputText" type="text" name="name" value="${profile.getName()}" size=35/> <p>

			<p class="labelProfil"> Prénom : </p>
			<p><input class="inputText" type="text" name="firstName" value="${profile.getFirstName()}" size=35/> <p>
			
			<p class="labelProfil"> Email : </p>
			<p><input class="inputText" type="email" name="email" value="${profile.getEmail()}" size=35/> <p>

			<p class="labelProfil"> Téléphone </p>
			<p><input class="inputText" type="text" name="phone" value="${profile.getPhone()}" size=35/> <p>
			
			<p class="labelProfil"> Rue : </p>
			<p><input class="inputText" type="text" name="street" value="${profile.getStreet()}" size=35/> <p>

			<p class="labelProfil"> Code Postal : </p>
			<p><input class="inputText" type="text" name="postCode" value="${profile.getPostCode()}" size=35/> <p>

			<p class="labelProfil"> Ville : </p>
			<p><input class="inputText" type="text" name="city" value="${profile.getCity()}" size=35/> <p>
			
			<p class="labelProfil"> Nouveau mot de passe*: </p>
			<p><input class="inputText" id="newPassword" type="password" name="newPassword" size=35 onkeyup="checkFunction()" required/> <p>
			
			<p class="labelProfil"> Confirmation mot de passe*: </p>
			<p><input class="inputText" id="confirmPassword" type="password" name="confirmPassword" size=35 onkeyup="checkFunction()" required/> <p>
			
			<p class="asterisque">* Vous devez définir un nouveau mot de passe pour pouvoir valider les modifications de votre profil.
			Il peut être identique à votre mot de passe actuel.</p>
			
			<p class="labelProfil"></p>
			<p><span id='message'></span><p>
		
		</div>

		<c:if test="${profile.getUser().equals(sessionScope.user)}">
			<div class="container3">
					<input type="submit" id="submit" name="validate" value="Valider" class="profileButton"/>
				
				<a href="<%=request.getContextPath()%>/ServletProfile?profile=${sessionScope.user}">
					<input type="button" name="cancel" value="Annuler" class="profileButton"/>
				</a>	
			</div>
		</c:if>
		
		</form>
		
		
		<script type="text/javascript">
		var checkFunction = function() {
			if (document.getElementById('newPassword').value ==
			    document.getElementById('confirmPassword').value) {
			    document.getElementById('message').style.color = 'green';
			    document.getElementById('message').innerHTML = 'Mots de passe identiques';
			    document.getElementById('submit').disabled = false;
			  } else {
			    document.getElementById('message').style.color = 'red';
			    document.getElementById('message').innerHTML = 'Mots de passe non identiques';
			    document.getElementById('submit').disabled = true;
			  }
		}
		
		</script>
		
		
		
	<footer class="footer" id="footerCell">
		<%@include file="Footer.jspf" %>
	</footer>

</div>
</body>
</html>