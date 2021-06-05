<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Reset password</title>
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

</head>
<body style="padding-top: 70px; background-color:  #041243; color: white;" >
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" >${firstName} ${lastName}</a>
			</div>
		
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/logout">Log out</a></li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="text" style="text-align: center">
			<h3 class="display-4">Reset Password</h3>
			<hr />
		</div>
		<form method="post" name="resetPasswordForm" id="resetPasswordForm" action="/reset_password"  style="width: 30em; margin-left: 26em" onsubmit="return(validateForm());">
				<div class="form-group">
				<label for="pwd">Password</label> <input type="password"	
					class="form-control" placeholder="Enter password" id="confirmPassword" name="password" required="required" >
			</div>
			<div class="form-group">
				<label for="pwd">Confirm Password</label> <input type="password"	
					class="form-control" placeholder="Confirm password" id="confirmPassword" name="confirmPassword" required="required" >
			</div>
			<button type="submit" class="btn btn-primary btn-lg btn-block" >Reset password</button>
		</form>
			
	</div>
	<script type="text/javascript">
		$('#resetPasswordForm').onsubmit(function() {
			location.reload();
		});
		
		function validateForm() {
			 var pass = document.getElementById("password");
			 var cpass = document.getElementById("confirmPassword");
			 
			  
			  if (pass.value != cpass.value) {
			   	cpass.setCustomValidity("Passwords don't match!");
			    return false;
			  }
			  return true;
			}
		
		
	</script>
</body>
</html>