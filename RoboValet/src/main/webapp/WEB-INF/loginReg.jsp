<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login and Reg</title>
<link href="css/stylesheet.css" rel="stylesheet" type="text/css">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<div class="container">
	<h2>RoboValet</h2>
	<div id="main">
		<div id="login" class="jstl-form">
						<form:form action="/login" method="post" modelAttribute="loginUser">
						<p>
							<form:label path="userName" class="form-label">Username</form:label>
							<form:errors path="userName" />
							<form:input class="form-control" path="userName" />
						</p>
						<p>
							<form:label path="password" class="form-label">Password</form:label>
							<form:errors path="password" />
							<form:password class="form-control" path="password"/>
						</p>
						<form:button class="btn btn-primary">Sign In</form:button>

					</form:form>
		</div>
		<div id="registration" class="jstl-form">
						<form:form action="/register" method="post" modelAttribute="newUser">
						<p>
							<form:label path="userName" class="form-label">Username</form:label>
							<form:errors path="userName" />
							<form:input class="form-control" path="userName" />
						</p>
						<p>
							<form:label path="passwordString" class="form-label">Password</form:label>
							<form:errors path="passwordString" />
							<form:password class="form-control" path="passwordString"/>
						</p>
						<p>
							<form:label path="confirmPassword" class="form-label">Confirm Password</form:label>
							<form:errors path="confirmPassword" />
							<form:password class="form-control" path="confirmPassword"/>
						</p>
						
						<form:button class="btn btn-primary">Register!</form:button>

					</form:form>
		</div>
	</div>
</div>



</body>
</html>