<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Users</title>

<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/css/dataTables.bootstrap.min.css"
	rel="stylesheet" />

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
<body style="padding-top: 70px; background-color:  #041243; color: white;" >
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
		<div class="text-center">
			<h3 class="display-3">Users</h3>
			<hr />
		</div>
		<div class="table">
			<table class="table table-striped table-bordered table-hover"
				style="font-size: 0.9em; color: grey" id="users">
				<thead>
					<tr>
						<th>Id</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email</th>
						<th>University Email</th>
						<th>Role</th>
						<th>Department</th>
						<th>Semester</th>
						<th>Active</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="usersList" items="${list}">
						<tr>
							<td>${usersList.id}</td>
							<td>${usersList.firstName}</td>
							<td>${usersList.lastName}</td>
							<td>${usersList.email}</td>
							<td>${usersList.secondaryEmail}</td>
							<td><c:if test="${usersList.role == 'STAFF'}"><p>ACADEMIC ADMIN</p></c:if>
							    <c:if test="${usersList.role != 'STAFF'}"><p>${usersList.role}</p></c:if>
							</td>
							<td>${usersList.department}</td>
							<td><c:if test="${usersList.semester != -1}"><p>${usersList.semester}</p></c:if>
                                <c:if test="${usersList.semester == -1}">No semester</c:if></td>
							<td>${usersList.active}</td>
							<td><a href="/users/edit/${usersList.id}">Edit</a><span>
									| </span><a href="/users/delete/${usersList.id}">Delete</a> <span>
									| </span><a href="/users/reset_password/${usersList.id}">Reset
									Password</a>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<hr />
		<div class="text">
			<h4 class="display-4">Add User</h4>
			<hr />
		</div>
		<form method="post" action="users" name="usersForm" id="usersForm">
			<div class="form-group">
				<label>First name</label><input name="firstName" type="text"
					class="form-control" placeholder="Enter First name"
					required=required id="firstName" />
			</div>
			<div class="form-group">
				<label>Last name </label><input name="lastName" type="text"
					class="form-control" placeholder="Enter Last name" id="lastName" required=required/>
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
            <label>Department</label><input name="department" type="text"
            	class="form-control" placeholder="Enter Department" id="department" required=required/>
            			</div>

            <div class="form-group" id="semesterContainer">
            				<label>Semester</label><input name="semester" type="number"
            					class="form-control" placeholder="Enter semester" id="semester" required=required/>
            			</div>
			<div class="form-group">
				<label>Email </label><input name="email" type="text"
					class="form-control" placeholder="Enter email" id="email" required=required/>
			</div>
			<div class="form-group">
            				<label>University Email </label><input name="secondary-email" type="text"
            					class="form-control" placeholder="Enter university email" id="secondary-email" required=required/>
            </div>
			<button type="submit" id="PageRefresh" class="btn btn-primary">Add</button>
		</form>
		<hr />
		<div class="text">
			<h4 class="display-4">Add Multiple Users</h4>
			<hr />
		</div>
		<form id="addmultipleusers" action="multiple_users" method="post"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="file">Select a CSV file</label><input id="file"
					type="file" name="file" class="form-control" required="required" /><br>
				<button type="submit" id="PageRefresh" class="btn btn-primary">Upload</button>
			</div>
		</form>

	</div>
	<script type="text/javascript">
        $(document).ready(function(){
            $("#semesterContainer").hide();
            $("#semester").removeAttr("required");
        });

	    $(function() {
        				$("#role").change(function() {
        					var value = $("#role").find(":selected").val();
        					if (value == "STAFF" || value == "ADMIN") {
        						$("#semesterContainer").hide();
        						$("#semester").removeAttr("required");

        					}else if(value == "STUDENT"){
        					    $("#semesterContainer").show();
                                $("#semester").attr("required", true);
        					}
        				});
        			});

		$('#usersForm').onsubmit(function() {
			location.reload();
		});
	</script>
</body>
</html>