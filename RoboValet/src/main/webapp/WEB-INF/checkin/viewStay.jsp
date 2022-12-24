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
			<h3>Stay Information:</h3>
			<p>Status: ${stay.status }</p>
			<p>Notes: ${stay.notes }</p>
			<p>Check In: ${stay.checkInTime}<p>
			<p>Check Out: ${stay.checkOutTime }<p>
			<p>Log:</p>
			<c:forEach items="${logs }" var="log">
				<p>${log }</p>
			</c:forEach>
		</div>
		<div>
			<h3>Customer Details:</h3>
			<p>Name: ${stay.customer.firstName} ${stay.customer.lastName}</p>
			<p>Phone: ${stay.customer.SMSPhone }</p>
			<p>Notify Customer by SMS? ${stay.customer.SMSPermission}</p>
		</div>
		<div>
			<h3>Vehicle Details:</h3>
			<p>Year: ${stay.car.year}</p>
			<p>Make: ${stay.car.make}</p>
			<p>Model: ${stay.car.model}</p>
			<p>Color: ${stay.car.color}</p>
		</div>

	</div>
	<div id="status">
		<c:choose>
			<c:when test="${stay.status == 'PARKING' }">
				<form action="/checkin/stay/${stay.id}/park" method="post">
					<div class="form-group">
						<label for="location">Location:</label>
						<input class="form-control" name="location" id="location"/>
					</div>
					<div class="form-group">
						<label for="notes">Other Notes:</label>
						<textarea name="notes" class="form-control" id="notes"></textarea>
					</div>
					<button class="btn btn-warning">Park Car</button>
				</form>
			</c:when>
			<c:when test="${stay.status == 'REQUESTED' }">
				<p>Is not parking</p>
			</c:when>
			<c:when test="${stay.status == 'FETCHING' }">
				<p>Is fretc</p>
			</c:when>
			<c:when test="${stay.status == 'READY' }">
				<p>Ready</p>
			</c:when>
			<c:when test="${stay.status == 'DELIVERED' }">
				<p>Is del</p>
			</c:when>
		</c:choose>
	</div>
	

	
</t:mainTemplate>



</body>
</html>