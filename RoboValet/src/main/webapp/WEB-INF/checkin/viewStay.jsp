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
<title>QuickValet</title>
<link href="/css/stylesheet.css" rel="stylesheet" type="text/css">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<t:mainTemplate>
	<div id="main">
		<div class="dataColumn">
			<h3>Stay Information:</h3>
			<div class="dataBlock">
				<p>Status: ${stay.status }</p>
				<p>Notes: ${stay.notes }</p>
				<p>Check In: ${stay.checkInTime}<p>
				<p>Check Out: ${stay.checkOutTime }<p>
			</div>
			<h3>Log</h3>
			<div class="dataBlock log">
				<c:forEach items="${logs }" var="log">
					<p>${log }</p>
				</c:forEach>
			</div>
		</div>
		<div>
			<div id="customerData">
				<div>
					<h3>Customer Details:</h3>
					<div class="dataBlock">
						<p>Name: ${stay.customer.firstName} ${stay.customer.lastName}</p>
						<p>Phone: ${stay.customer.SMSPhone }</p>
						<p>Notify Customer by SMS? ${stay.customer.SMSPermission}</p>
					</div>
				</div>
				<div>
					<h3>Vehicle Details:</h3>
					<div class="dataBlock">
						<p>Year: ${stay.car.year}</p>
						<p>Make: ${stay.car.make}</p>
						<p>Model: ${stay.car.model}</p>
						<p>Color: ${stay.car.color}</p>
					</div>
				</div>
			</div>
			<div class="dataColumn">
				<h4>Customer SMS Correspondance and Auto Responses:</h4>
				<div class="dataBlock log">
				<c:forEach items="${SMSlog}" var="message">
					<p>${message }</p>
				</c:forEach>
				</div>
			</div>
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
			<c:when test="${stay.status == 'PARKED' }">
				<form action="/checkin/stay/${stay.id}/request" method="post">
					<div class="form-group">
						<label for="notes">Notes:</label>
						<textarea name="notes" class="form-control" id="notes"></textarea>
					</div>
					<button class="btn btn-warning">Set to "Requested"</button>
				</form>
			</c:when>
			<c:when test="${stay.status == 'REQUESTED' }">
				<form action="/checkin/stay/${stay.id}/fetch" method="post">
					<div class="form-group">
						<label for="notes">Notes:</label>
						<textarea name="notes" class="form-control" id="notes"></textarea>
					</div>
					<button class="btn btn-warning">Fetch Car</button>
				</form>
			</c:when>
			<c:when test="${stay.status == 'FETCHING' }">
				<form action="/checkin/stay/${stay.id}/ready" method="post">
					<div class="form-group">
						<label for="notes">Notes:</label>
						<textarea name="notes" class="form-control" id="notes"></textarea>
					</div>
					<button class="btn btn-warning">Mark Vehicle Ready</button>
				</form>
			</c:when>
			<c:when test="${stay.status == 'READY' }">
				<form action="/checkin/stay/${stay.id}/deliver" method="post">
					<div class="form-group">
						<label for="notes">Notes:</label>
						<textarea name="notes" class="form-control" id="notes"></textarea>
					</div>
					<button class="btn btn-warning">Mark Delivered</button>
				</form>
			</c:when>
			<c:when test="${stay.status == 'DELIVERED' }">
				<p>Delivered on ${stay.checkOutTime}</p>
			</c:when>
		</c:choose>
	</div>
	

	
</t:mainTemplate>



</body>
</html>