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
				<tr><td>First Name:</td><td>${search.firstName }</td></tr>
				<tr><td>Last Name:</td><td>${search.lastName }</td></tr>
				<tr><td>Phone:</td><td>${search.SMSPhone }</td></tr>
				<tr><td>Email:</td><td>${search.email }</td></tr>
			</table>
			<h3>Matches...</h3>
			<table class="table">
				<thead>
					<tr>
						<th>Name</th>
						<th>Phone</th>
						<th>Email</th>
						<td>Select</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${possibleCustomers }" var="customer">
						<tr>
							<td>${customer.firstName} ${customer.lastName}</td>
							<td>${customer.SMSPhone }</td>
							<td>${customer.email }</td>
							<td>
								<form action="/checkin/customerSelect/${customer.id}" method="post">
									<button class="btn btn-warning">Select</button>
								</form>
							</td>						
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<form action="/checkin/customer/register" method="post">
				<button class="btn btn-success">Register New Customer</button>
			</form>
			<a href="/checkin/customer"><button class="btn btn-secondary">Back</button></a>
		</div>
	</div>

	
</t:mainTemplate>



</body>
</html>