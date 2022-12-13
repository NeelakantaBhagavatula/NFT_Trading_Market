<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Sale NFT</title>
	</head>
	<body>
		<div align="right">
			<a href="/profile">Back to Profile</a> | <a href="/logout">Logout</a>
		</div>
		<br>
		<div align="center">
		
			<table border="0" cellpadding="5">
				<caption>
					<h2>New Sale</h2>
					<h3>${msg}</h3>
				</caption>
				
				<form:form action="/sale/save" method="post" modelAttribute="sale">
				
					<form:hidden path="seller.id"/>
					<form:hidden path="nft.tokenId"/>
				
				<tr>
					<th align="right"><form:label path="expectedValue">Expected value:</form:label></th>
					<td><form:input type="number" required="required" path="expectedValue"/></td>
				</tr>
				<tr>	
					<th align="right"><form:label path="type">Sale type:</form:label></th>
					<td><form:select path="type" items="${saleTypes}" /></td>
				</tr>
				<tr>	
					<th align="right"><form:label path="cryptocurrency">Cryptocurrency:</form:label></th>
					<td><form:select path="cryptocurrency" items="${cryptos}" /></td>
				</tr>
				<tr>	
					<th></th>
					<td><form:button onclick="return confirm('Do you want to list this sale?');">List sale</form:button></td>
				</tr>
				
				</form:form>
				
			</table>
		
		</div>
	</body>
</html>
