<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Appointments</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/css/dataTables.bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"
	type="text/css" rel="stylesheet" />
		<link rel="stylesheet" href="css/style.css" >
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
</head>
<body style="padding-top: 70px; background-color:  #041243; color: white;">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/student">${firstName} ${lastName}</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="/student">Home</a></li>
				<li ><a href="/student/queries">Queries</a></li>
				<li class="active"><a href="/student/appointments">Appointments</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/reset_password">Reset password</a></li>
				<li><a href="/logout">Log out</a></li>
			</ul>
		</div>
	</nav>



	<div class="container">
		<c:if test="${not empty success}">
			<div class="alert alert-success alert-dismissible">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<p>
					<strong>${success}</strong>
				</p>
			</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger alert-dismissible">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<strong>${error}</strong>
			</div>
		</c:if>
		<div class="text">
			<h3 class="display-3 text-center">Appointments</h3>
			<hr />
		</div>
		<div class="table">

			<table class="table table-striped table-bordered table-hover"
				style="font-size: 0.9em; color: grey" id="appointments">
				<c:if test="${not empty appointments}">
					<thead>
						<tr>
							<th>Id</th>
							<th>Purpose</th>
							<th>Description</th>
							<th>Academic Admin</th>
							<th>Date</th>
							<th>Confirmed</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach var="appointments" items="${appointments}">
							<tr>
								<td>${appointments.id}</td>
								<td><p>${appointments.purpose}</p></td>
								<td><c:if test="${not empty appointments.description}"><p>${appointments.description}</p></c:if>
									<c:if test="${empty appointments.description}">No description</c:if></td>
								<td>${appointments.setBy.firstName}
									${appointments.setBy.lastName}</td>
								<td>${appointments.date}</td>
								<td><c:if test="${not appointments.isConfirmed}">No</c:if><c:if test="${appointments.isConfirmed}">Yes</c:if></td>
								<td><c:if test="${not appointments.isConfirmed}"><a href="/student/appointments/${appointments.id}">Confirm appointment</a></c:if></td>
									
							</tr>
						</c:forEach>
					</tbody>
				</c:if>
				<c:if test="${empty appointments}">
					<tbody>
						<tr>
							<td>No appointments yet!</td>
						</tr>
					</tbody>
				</c:if>
			</table>

		</div>
		<hr />
		
		
	</div>
	<script type="text/javascript">
		$('#queryForm').onsubmit(function() {
			location.reload();
		});
	</script>
</body>
</html>