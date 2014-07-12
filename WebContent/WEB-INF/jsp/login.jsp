<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" 
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
</head>
<body>
<div class="container">
	    <div class="row">
			<div class="span12">
				<form class="form-horizontal" action='loginValidation' method="POST">
				  <fieldset>
				    <div id="legend">
				      <legend class="">Login</legend>
				    </div>
				    <div class="control-group">
				      <!-- Username -->
				      <label class="control-label"  for="username">Username</label>
				      <div class="controls">
				        <input type="text" id="username" name="username" placeholder="" class="input-xlarge">
				      </div>
				    </div>
				    <div class="control-group">
				      <!-- Password-->
				      <label class="control-label" for="password">Password</label>
				      <div class="controls">
				        <input type="password" id="password" name="password" placeholder="" class="input-xlarge">
				      </div>
				    </div>
				    <div class="controls">
				    	<label> <font color="red">${message}</font></label>
				    </div>
				    <div class="control-group">
				      <!-- Button -->
				      <div class="controls">
				        <button class="btn btn-success" type="submit">Login</button>
				      </div>
				    </div>
				  </fieldset>
				</form>
				
				<form method="GET" action="register">
					<button class="btn" type="submit">Register</button>
				</form>
			</div>
		</div>
	</div>
	
</body>
</html>