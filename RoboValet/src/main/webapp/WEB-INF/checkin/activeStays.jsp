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

<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<link href="/css/stylesheet.css" rel="stylesheet" type="text/css">
</head>
<body>
<t:mainTemplate>
	<div id="main">
		<div id="activeStays">
			
			<h3>Active Stays</h3>
			<table class="table">
				<thead>
					<tr>
						<th>Check-in Time</th>
						<th>Plate</th>
						<th>Make</th>
						<th>Model</th>
						<th>Year</th>
						<th>Color</th>
						<th>Customer Name</th>
						<th>SMS</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${activeStays }" var="stay">
						<tr class="stayRow">
							<td><a href="/checkin/stay/view/${stay.id}">${stay.checkInTime}</a></td>
							<td><a href="/checkin/stay/view/${stay.id}">${stay.car.plate }</a></td>
							<td><a href="/checkin/stay/view/${stay.id}">${stay.car.make }</a></td>
							<td><a href="/checkin/stay/view/${stay.id}">${stay.car.model }</a></td>
							<td><a href="/checkin/stay/view/${stay.id}">${stay.car.year }</a></td>
							<td><a href="/checkin/stay/view/${stay.id}">${stay.car.color }</a></td>
							<td><a href="/checkin/stay/view/${stay.id}">${stay.customer.firstName } ${stay.customer.lastName }</a></td>
							<td><a href="/checkin/stay/view/${stay.id}">${stay.customer.SMSPhone }</a></td>
							<td><a href="/checkin/stay/view/${stay.id}">${stay.status }</a></td>						
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<a href="/checkin/customer"><button class="btn btn-secondary">Check In</button></a>
		</div>
	</div>

	
</t:mainTemplate>



</body>
</html>