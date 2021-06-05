<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Reports</title>
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
<link href="/css/message.css" type="text/css" rel="stylesheet" />
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
				<a class="navbar-brand" href="/staff">${firstName} ${lastName}</a>
			</div>
			<ul class="nav navbar-nav">
				<li ><a href="/staff">Home</a></li>
				<li><a href="/staff/queries">Queries</a></li>
				<li><a href="/staff/appointments">Appointments</a></li>
				<li  class="active"><a href="/staff/reports">Reports</a></li>
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
			<h3 class="display-3 text-center">Reports</h3>
			<hr />
		</div>
		<div class="table">
			<table class="table table-striped table-bordered table-hover"
				style="font-size: 0.9em; color: grey" id="users">
				<thead>
					<tr>
						<th>File name</th>
						<th>File type</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="reports" items="${reports}">
						<tr>
							<td>${reports.fileName}</td>
							<td>${reports.fileType}</td>
							
							<td><a href="/reports/download/${reports.id}">View</a>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<hr/>
		<div class="text">
			<h4 class="display-4">Generate monthly report</h4>
			<hr />
		</div>
		
		<form method="post" action="/staff/reports/monthly" name="reportForm" id="reportForm">
			<div class="form-group">
				<label>Month</label><select name="month" class="form-control"
					id="month" required>
					<option value="">Select a month</option>
					<option value="0">January</option>
					<option value="1">February</option>
					<option value="2">March</option>
					<option value="3">April</option>
					<option value="4">May</option>
					<option value="5">June</option>
					<option value="6">July</option>
					<option value="7">August</option>
					<option value="8">September</option>
					<option value="9">October</option>
					<option value="10">November</option>
					<option value="11">December</option>
				</select>
			</div>
			<div class="form-group">
				<label>Semester</label><select name="semester" class="form-control"
            					id="semester" required>
            					<option value="">Select a semester</option>
            					<option value="1">1st</option>
            					<option value="2">2nd</option>
            					<option value="3">3rd</option>
            					<option value="4">4th</option>
            					<option value="5">5th</option>
            					<option value="6">6th</option>
            					<option value="7">7th</option>
            					<option value="8">8th</option>
            				</select>
            </div>

			<div class="form-group">
			    <label>Department</label>
			    <input type="text" class="form-control" name="department" placeholder="Enter department"/>
			</div>
			<button type="submit" id="PageRefresh" class="btn btn-primary">Generate</button>
		</form>
		<hr/>
		<div class="text">
        			<h4 class="display-4">Generate yearly report</h4>
        			<hr />
        		</div>
        		<form method="post" action="/staff/reports/yearly" name="reportForm" id="reportForm">
        			<div class="form-group">
        				<label>Year</label><input type="text" placeholder="Enter a year" name="year" class="form-control"
        					id="year" required/>
        			</div>

                    <div class="form-group">
                    <label>Semester</label><select name="semester" class="form-control"
                                    id="semester" required>
                                    <option value="">Select a semester</option>
                                    <option value="1">1st</option>
                                    <option value="2">2nd</option>
                                    <option value="3">3rd</option>
                                    <option value="4">4th</option>
                                    <option value="5">5th</option>
                                    <option value="6">6th</option>
                                    <option value="7">7th</option>
                                    <option value="8">8th</option>
                                </select>
                    </div>

                    <div class="form-group">
                        <label>Department</label>
                        <input type="text" class="form-control" name="department" placeholder="Enter department"/>
                    </div>
        			<button type="submit" id="PageRefresh" class="btn btn-primary">Generate</button>
        		</form>
        		<hr/>
	</div>
	<script type="text/javascript">
		$('#queryForm').onsubmit(function() {
			location.reload();
		});
	</script>
</body>
</html>