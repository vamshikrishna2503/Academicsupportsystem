<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Queries</title>
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
				<li class="active"><a href="/staff/queries">Queries</a></li>
				<li><a href="/staff/appointments">Appointments</a></li>
				<li><a href="/staff/reports">Reports</a></li>

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
			<h3 class="display-3 text-center">Reply Query</h3>
			<hr />
		</div>
		<form method="post" action="/staff/queries" name="queryForm" id="queryForm">

			<div class="form-group">
				<label>Query ID</label><input type="text" name="id" 
					class="form-control" placeholder="Write your reply here!"
					required=required id="id" value="${query_id}" readonly/>
			</div>
			<div class="form-group">
				<label>Query</label><textarea name="query" rows="4" cols="200" style=" resize: vertical"
					class="form-control" placeholder="Write your reply here!"
					required=required id="query" readonly>${query_message}</textarea>
			</div>
				<label>Reply</label><textarea name="reply" rows="4" cols="200" style=" resize: vertical"
					class="form-control" placeholder="Write your reply here!"
					required=required id="reply"></textarea>
			</div>


            <div class="text-center" >
            			<button type="submit" id="PageRefresh" class="btn btn-primary">Send</button>
            			<hr />
            		</div>
		</form>

	</div>
	<script type="text/javascript">
		$('#queryForm').onsubmit(function() {
			location.reload();
		});
	</script>
</body>
</html>