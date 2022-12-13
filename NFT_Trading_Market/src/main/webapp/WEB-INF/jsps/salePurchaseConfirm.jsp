<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Purchase sale confirmation</title>
		<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20,300,0,0" />
		<style>
		
  			.action_btn {
			    display: flex;
			    justify-content: center;
			    gap: 10px;
			}
			
			.action_btn>a {
			    text-decoration: none;
			    color: #444;
			    background: #fff;
			    border: 1px solid;
			    display: inline-block;
			    padding: 7px 20px;
			    font-weight: bold;
			    border-radius: 3px;
			    transition: 0.3s ease-in-out;
			}
			
			.action_btn>a:nth-child(1) {
			    border-color: #00000017;
			}
			
			.action_btn>a:nth-child(2) {
			    border-color: orange;
			}
			
			.action_btn>a:hover {
			    box-shadow: 0 3px 8px #0003;
			}
		</style>
	</head>
	<body>
	
	<div align="right">
		<a href="/profile">Back to Profile</a> | <a href="/logout">Logout</a>
	</div>
	<br>
	<div align="center">
		
		<table border="0" cellpadding="5" style="text-align:left; vertical-align:top;" >
			
			<caption> <h3>Sale purchase</h3> </caption>
			
			<tr>
				<th>Name: </th>
				<td>${sale.nft.name}</td>
			</tr>
			<tr>
				<th>Type:</th>
				<td>${sale.nft.type}</td>
			</tr>
			
			<tr>	
				<th>Description:</th>
				<td>${sale.nft.description}</td>
			</tr>
			
			<tr>
				<th>Price:</th>
				<td><fmt:formatNumber type="number" maxFractionDigits="5" value="${sale.expectedValue}"/> ${sale.cryptocurrency.symbol}</td>
			</tr>
			
			<tr>
				<th style="vertical-align:top;">Image:</th>
				<td><img src="${sale.nft.imageUrl}" alt="NFT" style="width:200px;"></td>
			</tr>
			<tr>
				<th></th>
				<td style="text-align:right;"> 
					<span class="action_btn"><a href="/sale/purchase/${sale.id}">Confirm purchase</a></span>
				</td>
			</tr>
			
		</table>
		
		</div>
		
	</body>
	
</html>