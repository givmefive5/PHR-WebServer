<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	Test1: Retrieve and Filter Posts Retrieved from FB
	<form method="POST" action="test">
		FB Access Token from Graph API:<input type="text" name="userFBAccessToken"/><br>
		<input type="submit"/>
	</form>
	<br><br>
	Test2: Testing the Filter tool 
	<form method="POST" action="test2">
		<input type="submit"/>
	</form>
	
	Test4: Testing the image encoding/saving
	<form method="POST" action="test4">
		<input type="submit"/>
	</form>
	
	Test5: FatSecret
	<form method="POST" action="test5">
		Search Using FatSecret:<input type="text" name="searchQuery"/><br>
		<input type="submit"/>
	</form>
	Test6: Test GetTagged
	<form method="POST" action="test6">
		Input Access Token:<input type="text" name="userFBAccessToken"/><br>
		<input type="submit"/>
	</form>
	
	Test7: FoodSearch
	<form method="POST" action="test7">
		Search :<input type="text" name="searchQuery"/><br>
		<input type="submit"/>
	</form>
</body>
</html>