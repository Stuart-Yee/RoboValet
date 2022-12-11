<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "t" tagdir = "/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>RoboValet</title>
<link href="css/stylesheet.css" rel="stylesheet" type="text/css">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<t:mainTemplate>
	<div id="main">
		<h2>Customer Registration</h2>
			<div id="registration" class="jstl-form">
						<form:form action="/customers/register" method="post" modelAttribute="newCustomer">
						<p>
							<form:label path="firstName" class="form-label">First Name</form:label>
							<form:errors path="firstName" class="red-letters" />
							<form:input class="form-control" path="firstName" required="required"/>
						</p>
						<p>
							<form:label path="lastName" class="form-label">Last Name</form:label>
							<form:errors path="lastName" class="red-letters"/>
							<form:input class="form-control" path="lastName" required="required"/>
						</p>
						<p>
							<form:label path="SMSPhone" class="form-label">SMS Phone</form:label>
							<form:errors path="SMSPhone" class="red-letters"/>
							<form:input class="form-control" path="SMSPhone" required="required"/>
						</p>
						<p>
							<form:label path="email" class="form-label">Email</form:label>
							<form:errors path="email" class="red-letters"/>
							<form:input class="form-control" path="email" required="required"/>
						</p>
						<p>
							<form:label path="SMSPermission" class="form-label">SMS Phone</form:label>
							<form:errors path="SMSPermission" class="red-letters"/>
							<form:checkbox path="SMSPermission" value="true"/>  
						</p>
						
						
						
						<form:button class="btn btn-primary">Register!</form:button>

					</form:form>
					<a href="/logout"><button class="btn btn-warning">Do this Later</button></a>
		</div>
	</div>

	
</t:mainTemplate>



</body>
</html>