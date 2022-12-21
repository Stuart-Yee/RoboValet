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
		<h2>Vehicle Details</h2>
			<div id="registration" class="jstl-form">
						<form:form action="/checkin/car" method="post" modelAttribute="carDetails">
						<p>
							<form:label path="plate" class="form-label">Plate</form:label>
							<form:errors path="plate" class="red-letters" />
							<form:input class="form-control" path="plate" required="required"/>
						</p>
						<p>
							<form:label path="make" class="form-label">Make</form:label>
							<form:errors path="make" class="red-letters"/>
							<form:input class="form-control" path="make" required="required"/>
						</p>
						<p>
							<form:label path="model" class="form-label">Model</form:label>
							<form:errors path="model" class="red-letters"/>
							<form:input class="form-control" path="model" required="required"/>
						</p>
						<p>
							<form:label path="year" class="form-label">Year</form:label>
							<form:errors path="year" class="red-letters"/>
							<form:input class="form-control" path="year" required="required"/>
						</p>
						<p>
							<form:label path="color" class="form-label">Color</form:label>
							<form:errors path="color" class="red-letters"/>
							<form:input class="form-control" path="color" required="required"/>  
						</p>
						<p>
							<form:label path="notes" class="form-label">Color</form:label>
							<form:errors path="notes" class="red-letters"/>
							<form:textarea class="form-control" path="notes"/>  
						</p>
						
						
						
						<form:button class="btn btn-primary">Find Customer</form:button>

					</form:form>
					<a href="/logout"><button class="btn btn-warning">Do this Later</button></a>
		</div>
	</div>

	
</t:mainTemplate>



</body>
</html>