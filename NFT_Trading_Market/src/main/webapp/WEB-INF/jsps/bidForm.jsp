<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Make a bid to an Auction</title>
	</head>
	<body>
		<h2>New bid</h2>
		
		<form:form action="makeBid" method="post" modelAttribute="bid">
			
			<form:hidden path="user.id"/>
			<form:hidden path="sale.id"/>
			
			<form:label path="bidValue">Bid value:</form:label>
            <form:input path="bidValue"/><br/>
            
			<form:button>Make bid</form:button>
	
	    </form:form>
	    
	</body>
</html>
