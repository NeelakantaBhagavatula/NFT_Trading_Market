<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>List NFT to sale</title>
	</head>
	<body>
		<h2>New sale</h2>
		
		<form:form action="save" method="post" modelAttribute="sale">
			
			<form:hidden path="seller.id"/>
			
			<form:label path="expectedValue">Price:</form:label>
            <form:input path="expectedValue"/><br/>
            
			<form:label path="type">Sale type:</form:label>
	        <form:select path="type" items="${saleTypes}" /><br/>
            
	    	<form:label path="cryptocurrency">Cryptocurrency:</form:label>
	        <form:select path="cryptocurrency" items="${cryptos}" /><br/>
	                     
	    	<form:button>Save</form:button>
	
	    </form:form>
	    
	</body>
</html>
