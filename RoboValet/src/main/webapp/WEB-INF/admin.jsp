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
	<h2>Admin Stuff</h2>
	<table class="table">
		<thead>
			<tr>
				<th>Username</th>
				<th>Link Customer</th>
				<th>Link Employee</th>
				<th>Delete User</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${unassignedUsers}" var="user">
				<tr>
					<td>${user.userName}</td>
					<td>
						<form action="/users/link/customer" method="post">
							<input type="hidden" name="userId" value="${user.id}">
							<select name="customerId">
								<c:forEach items="${unassignedCustomers}" var="customer">
									<option value="${customer.id}">${customer.firstName} ${customer.lastName}</option>
								</c:forEach>
							
							</select>
							<button class="btn btn-primary">Link</button>
						</form>
					</td>
					<td>
						<form action="/users/link/employee" method="post">
							<input type="hidden" name="userId" value="${user.id}">
							<select name="customerId">
								<c:forEach items="${unassignedEmployees}" var="employee">
									<option value="${employee.id}">${employee.firstName} ${employee.lastName}</option>
								</c:forEach>
							
							</select>
							<button class="btn btn-primary">Link</button>
						</form>
					</td>
					<td>
						<form action="/users/delete" method="post">
							<input type="hidden" name="userId" value="${user.id}">
							<button class="btn btn-danger">Delete</button>
						</form>
					<td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<hr>
	<table class="table">
		<thead>
			<tr><th>Username</th><th>Enable as Employee?</th></tr>
		</thead>
			<c:forEach items="${notEmployeeEnabled}" var="user">
				<tr>
					<td>${user.userName}</td>
					<td>
						<form action="/user/enable/${user.id}" method="post">
							<button class="btn btn-danger">Enable</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		<tbody>
		</tbody>
	</table>
</t:mainTemplate>



</body>
</html>