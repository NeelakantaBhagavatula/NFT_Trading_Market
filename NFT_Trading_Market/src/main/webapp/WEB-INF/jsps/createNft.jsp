<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Create NFT</title>
	</head>
	<body>
		<div align="right">
			<a href="/profile">Back to Profile</a> | <a href="/logout">Logout</a>
		</div>
		<br>
		<div align="center">
		
			<table border="0" cellpadding="5">
				<caption>
					<h2>Create NFT</h2>
					<h3>${msg}</h3>
				</caption>
				
				<form action="/createNft" method="post">
				
				<tr>
					<th align="right">Name:</form:label></th>
					<td><input type="text" name="name" required="required"/></td>
				</tr>
				<tr>	
					<th align="right">Type:</th>
					<td><input type="text" name="type" required="required"/></td>
				</tr>
				<tr>	
					<th align="right">Description:</th>
					<td><input type="text" name="description" required="required"/></td>
				</tr>
				<tr>	
					<th align="right">Image URL:</th>
					<td><input type="url" name="imageUrl" placeholder="https://example.com" required="required"/></td>
				</tr>
				<tr>	
					<th align="right">Asset URL:</th>
					<td><input type="url" name="assetUrl" placeholder="https://example.com" required="required"/></td>
				</tr>
				<tr>	
					<th></th>
					<td><input type="submit" onclick="return confirm('Do you want to create this NFT?');" type="submit" value="Create NFT"/>
				</tr>
				
				</form>
				
			</table>
			
		</div>
	
	</body>
</html>