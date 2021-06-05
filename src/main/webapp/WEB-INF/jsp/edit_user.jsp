<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Edit users</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/css/dataTables.bootstrap.min.css"
	rel="stylesheet" />
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
<body style="padding-top: 70px; background-color:  #041243; color: white;">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/admin">${firstName} ${lastName}</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="/admin">Home</a></li>
				<li class="active"><a href="/admin/users">Users</a></li>
				<li><a href="/admin/reports">Reports</a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="/logout">Log out</a></li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<c:if test="${not empty success}">
			<div class="alert alert-success alert-dismissible">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<p><strong>${success}</strong></p>
			</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger alert-dismissible">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<strong>${error}</strong>
			</div>
		</c:if>
		<div class="text">
			<h4 class="display-4">Edit User</h4>
			<hr />
		</div>
		
		<form method="post" action="users/edit" name="usersForm" id="usersForm">
			<div class="form-group">
				<label>Id</label><input name="id" type="text"
					class="form-control" value="${id}"
					readonly id="id" />
			</div>
			<div class="form-group">
				<label>First name</label><input name="firstName" type="text"
					class="form-control" placeholder="Enter First name"
					required=required id="firstName" />
			</div>
			<div class="form-group">
				<label>Last name </label><input name="lastName" type="text"
					class="form-control" placeholder="Enter Last name" id="lastName" />
			</div>
			<div class="form-group">
				<label>Role </label><select name="role" class="form-control"
					id="role" required>
					<option value="">Select an account type</option>
					<option value="ADMIN">ADMIN</option>
					<option value="STAFF">ACADEMIC ADMIN</option>
					<option value="STUDENT">STUDENT</option>
				</select>
			</div>
			<div class="form-group">
				<label>Active </label><select name="isActive" class="form-control"
					id="isActive" required>
					<option value="">Select active or not active</option>
					<option value="true">Active</option>
					<option value="false">Not active</option>
				</select>
			</div>
			<div class="form-group">
				<label>Email </label><input name="email" type="text"
					class="form-control" placeholder="Enter email" id="email" />
			</div>
			<div class="form-group">
				<label for="pwd">Password:</label> <input type="password"	
					class="form-control" placeholder="Enter password" id="pwd" name="password" required="required">
			</div>
			<button type="submit" id="PageRefresh" class="btn btn-primary">Update</button>
			
		</form>

		</div>
	<script type="text/javascript">
		$('#usersForm').onsubmit(function() {
			location.reload();
		});
	</script>
</body>
</html>