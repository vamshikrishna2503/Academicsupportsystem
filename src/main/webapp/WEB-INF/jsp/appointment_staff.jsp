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
<body style="padding-top: 70px ; background-color:  #041243; color: white;">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/staff">${firstName} ${lastName}</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="/staff">Home</a></li>
				<li><a href="/staff/queries">Queries</a></li>
				<li class="active"><a href="/staff/appointments">Appointments</a></li>
				<li><a href="/staff/reports">Reports</a></li>
				<li><a href="/staff/message">Message</a></li>
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
							<th>Student</th>
							<th>Date</th>
							<th>Confirmed</th>

						</tr>
					</thead>
					<tbody>

						<c:forEach var="appointments" items="${appointments}">
							<tr>
								<td>${appointments.id}</td>
								<td><p>${appointments.purpose}</p></td>
								<td><c:if test="${not empty appointments.description}">
										<p>${appointments.description}</p>
									</c:if> <c:if test="${empty appointments.description}">No description</c:if></td>
								<td>${appointments.setFor.id} - ${appointments.setFor.firstName}
									${appointments.setFor.lastName}</td>
								<td>${appointments.date}</td>
								<td><c:if test="${not appointments.isConfirmed}">No</c:if>
									<c:if test="${appointments.isConfirmed}">Yes</c:if></td>

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
		<div class="text">
			<h4 class="display-4">Set an appointment</h4>
			<hr />
		</div>
		<form method="post" action="/staff/appointments" name="queryForm"
			id="queryForm">
			<div class="form-group">
				<div class="form-group">
					<label>Student</label><select name="student" class="form-control"
						id="student" required>
						<option value="">Select a student</option>
						<c:forEach var="students" items="${students}">
							<option value="${students.id}">${students.id}-
								${students.firstName} ${students.lastName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Purpose</label><input type="text" name="purpose"
						class="form-control" placeholder="Enter purpose" required=required
						id="purpose" />
				</div>
				<div class="form-group">
					<label>Description</label>
					<textarea name="description" rows="4" cols="200"
						style="resize: vertical" class="form-control"
						placeholder="Write your description here!" id="description"></textarea>
				</div>
				<div class="form-group">
					<label>Date</label><input type="datetime-local" name="date"
						class="form-control" placeholder="MM/DD/YYYY 00:00 AM/PM" required=required
						id="date" />
				</div>
			</div>

			<button type="submit" id="PageRefresh" class="btn btn-primary">Send</button>
		</form>
	</div>

	<script type="text/javascript">
		$('#queryForm').onsubmit(function() {
			location.reload();
		});
	</script>
</body>
</html>