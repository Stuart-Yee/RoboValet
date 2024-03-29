<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/css/stylesheet.css">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<title>QuickValet</title>
</head>
<body>
	<div class="container">
<h1>QuickValet</h1>
<a href="/checkin/customer">Check In</a> | <a href="/checkin/active">Active Vehicles</a>
<c:choose>
	<c:when test="${sessionScope.id != null }">
	| <a href="/logout">Logout</a>
	</c:when>
	<c:otherwise>
	| <a href="/login">Login</a>
	</c:otherwise>
</c:choose>


		<hr>


		<jsp:doBody />


		<div id="footer">
			<hr>
			<p>View our Privacy Policy - �2022 Simflario (Other
				Footer stuff here)</p> <a href="/backdoor"><button class="btn btn-danger">BACK DOOR!</button></a>
		</div>
	</div>
</body>
</html>