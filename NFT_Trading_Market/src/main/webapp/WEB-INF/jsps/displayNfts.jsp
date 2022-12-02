<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display NFT's</title>
</head>
<body>
	<div align="center">
		<table border="1" cellpadding="5">
			<caption>
				<h2>List of NFT's</h2>
			</caption>
			<tr>
				<th>Name</th>
				<th>Type</th>
				<th>Description</th>
				<th>Image URL</th>
				<th>Asset URL</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${nfts}" var="nft">
				<tr>
					<td><c:out value="${nft.name}" /></td>
					<td><c:out value="${nft.type}" /></td>
					<td><c:out value="${nft.description}" /></td>
					<td><c:out value="${nft.imageUrl}" /></td>
					<td><c:out value="${nft.assetUrl}" /></td>
					<td><a href="sellNft?nftId=${nft.tokenId}">Sell</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br>
	<div align="center">
		<a href="profile">Back to Profile</a><br/>
		<a href="logout">Logout</a>
	</div>
</body>
</html>