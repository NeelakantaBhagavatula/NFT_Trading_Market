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
		<div align="right">
			<a href="/profile">Back to Profile</a> | <a href="/logout">Logout</a>
		</div>
		<br>
		<div align="center">
		
			<table border="0" cellpadding="5">
			
				<caption>
					<h2>Make an offer</h2>
					<h3>${msg}</h3>
				</caption>
				
				<form action="/sale/saveOffer" method="post">
				
					<input type="hidden" name="saleId" value="${saleId}" />
				
					<tbody>
						<tr>
						    <td colspan="3">Expiration Time</td>
						</tr>
						<tr>
						    <td>Hour</td>
						    <td>Minute</td>
						    <td>Second</td>
						</tr>
						<tr>
						    <td><input type="number" id="hour" name="hour" min="0" max="99" value="0"></td>
						    <td><input type="number" id="minute" name="minute" min="0" max="59" value="0"></td>
						    <td><input type="number" id="second" name="second" min="0" max="59" value="0"></td>
						</tr>
						<tr>
						    <td colspan="3">Bid value</td>
						</tr>
						<tr>
						    <td colspan="3"><input type="number" id="bidValue" step=".000001" min="${minPrice}" name="bidValue" value="0.0" /></td>
						</tr>
						<tr>
						    <td colspan="3"><input type="submit" value="Send offer" /></td>
						</tr>
						
					</tbody>
					
					</form>
	
			</table>
		
		</div>
		
	</body>

</html>
