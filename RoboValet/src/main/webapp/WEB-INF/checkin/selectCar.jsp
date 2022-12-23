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
		<div id="searchResults">
			<h3>Search Details</h3>
			<table class="table">
				<tr><td>Plate:</td><td>${carInfo.plate }</td></tr>
				<tr><td>Make:</td><td>${carInfo.make}</td></tr>
				<tr><td>Model:</td><td>${carInfo.model }</td></tr>
				<tr><td>Year:</td><td>${carInfo.year }</td></tr>
				<tr><td>Color:</td><td>${carInfo.color }</td></tr>
			</table>
			<h3>Matches...</h3>
			<table class="table">
				<thead>
					<tr>
						<th>Plate</th>
						<th>Make</th>
						<th>Model</th>
						<td>Year</td>
						<td>Color</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${results }" var="car">
						<tr>
							<td>${car.plate}</td>
							<td>${car.make }</td>
							<td>${car.model }</td>
							<td>${car.year }</td>
							<td>${car.color }</td>
							<td>
								<form action="/checkin/car/select/${car.id}" method="post">
									<button class="btn btn-warning">Select</button>
								</form>
							</td>						
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<form action="/checkin/car/register" method="post">
				<button class="btn btn-success">Register New Car</button>
			</form>
			<a href="/checkin/car"><button class="btn btn-secondary">Back</button></a>
		</div>
	</div>

	
</t:mainTemplate>



</body>
</html>