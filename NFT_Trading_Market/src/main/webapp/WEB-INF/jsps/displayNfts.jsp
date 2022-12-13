<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display NFT's</title>
<link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div align="right">
		<a href="/profile">Back to Profile</a> | <a href="/logout">Logout</a>
	</div>
	<br>
	<c:choose>
		<c:when test="${nfts.size() == 0}">
			<div align="center"> <h3>You don't have any NFT yet.</h3></div>
		</c:when>
		<c:otherwise>
						
			<div align="center">
				<table border="0" cellpadding="5" style="width:70%">
					<caption>
						<h2>My NFT's</h2>
						<h3>${msg}</h3>
					</caption>
					<thead>
					<tr>
						<th></th>
						<th>Name</th>
						<th>Type</th>
						<th>Description</th>
						<th>Asset URL</th>
						<th></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${nfts}" var="nft">
						<tr>
							<td style="vertical-align:center; text-align:center;"><img src="${nft.imageUrl}" alt="${nft.name}"></td>
							<td>${nft.name}</td>
							<td>${nft.type}</td>
							<td>${nft.description}</td>
							<td>${nft.assetUrl}</td>
							<td><span class="action_btn"><a href="/sale/new/${nft.tokenId}">Sell NFT</a></span></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
	
		</c:otherwise>
	</c:choose>
</body>
</html>