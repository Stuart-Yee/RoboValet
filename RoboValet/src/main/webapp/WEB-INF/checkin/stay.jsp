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
		<div>
			<h3>Customer Details:</h3>
			<p>Name: ${customer.firstName} ${customer.lastName}</p>
			<p>Phone: ${customer.SMSPhone }</p>
			<p>Notify Customer by SMS? ${customer.SMSPermission}</p>
		</div>
		<div>
			<h3>Vehicle Details:</h3>
			<p>Year: ${car.year}</p>
			<p>Make: ${car.make}</p>
			<p>Model: ${car.model}</p>
			<p>Color: ${car.color}</p>
		</div>
		<div>
			<h3>Final Details:</h3>
			<form action="/checkin/stay" method="post">
			<p>Notes:</p>
			<textarea name="notes"></textarea>
			<button class="btn btn-success">Check In</button>
			</form>
		</div>
	</div>

	
</t:mainTemplate>



</body>
</html>