<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Connexion</title>
</head>

<body>
	<div id="login">
		<h1>
			<img width="250" src="images/logo.png" alt="logo" />
		</h1>
	</div>
	
	<form action="" method = "POST">
		<div id="id">
			<label>Identifiant : </label>
			<input type="text" class="idField"/>
		</div>
	
		<div id="password">
			<label>Mot de passe : </label>
			<input type="text" class="passwordField"/>
		</div>
	
		<div id="validation">
			<input type="submit" name="delete" value="Connexion"/>
			<div>
				<input type="checkbox" class="remember">
				<label>Se souvenir de moi</label>
			</div>
	
			<div>
				<a class="link" href="${pageContext.request.contextPath}/ForgetPassword">Mot de passe oublié</a>
			</div>
		</div>
	</form>
	
	<form action="" method="GET">
		<input type="submit" name="delete" value="Créer un compte"/>
	</form>

</body>
</html>