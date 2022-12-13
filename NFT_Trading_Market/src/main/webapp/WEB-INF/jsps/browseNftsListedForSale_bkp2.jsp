<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Browse NFT's Listed for Sale</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20,300,0,0" />
</head>
<body>
	<div align="center">
		<table border="1" cellpadding="5">
			<caption>
				<h2>Priced sales</h2>
				<c:if test="${msg}"><h3>${msg}</h3></c:if>
			</caption>
			<tr>
				<th colspan="4">NFT</th>
				<th colspan="3">Sale</th>
			</tr>
			<tr>
				<th>Name</th>
				<th>Type</th>
				<th>Description</th>
				<th>Image</th>
				<th>Date</th>
				<th>Status</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${priced}" var="sale">
			
			<c:choose>
				<c:when test="${sale.closingTime == null}">
					<c:set var = "status" value = "Pending"/>
				</c:when>
				<c:when test="${sale.buyer == null}">
					<c:set var = "status" value = "Canceled"/>
				</c:when>
				<c:otherwise>
					<c:set var = "status" value = "Sold"/>
				</c:otherwise>
			</c:choose>
				
				<tr>
					<td><c:out value="${sale.nft.name}" /></td>
					<td><c:out value="${sale.nft.type}" /></td>
					<td><c:out value="${sale.nft.description}" /></td>
					<td><c:out value="${sale.nft.imageUrl}" /></td>
					<td><fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${sale.creationTime}" /></td>
					<td><c:out value="${status}" /></td>
					<td>
						<c:if test = "${status == 'Pending'}">
							<a href="/sale/cancel/<c:out value="${sale.id}" />">
  								<span class="material-symbols-outlined">cancel</span>
							</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br>
	<div align="center">
		<table border="1" cellpadding="5">
			<caption>
				<h2>Auction sales</h2>
				<c:if test="${msg}"><h3>${msg}</h3></c:if>
			</caption>
			<tr>
				<th colspan="4">NFT</th>
				<th colspan="5">Sale</th>
			</tr>
			<tr>
				<th>Name</th>
				<th>Type</th>
				<th>Description</th>
				<th>Image</th>
				<th>Date</th>
				<th>Highest Offer</th>
				<th>Expiration time</th>
				<th>Status</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${auction}" var="sale">
			
				<c:choose>
					<c:when test="${sale.closingTime == null}">
						<c:set var = "status" value = "Pending"/>
					</c:when>
					<c:when test="${sale.buyer == null}">
						<c:set var = "status" value = "Canceled"/>
					</c:when>
					<c:otherwise>
						<c:set var = "status" value = "Sold"/>
					</c:otherwise>
				</c:choose>
				
				<c:set var = "bid" value = "${sale.bids[fn:length(sale.bids)-1]}"/>
				
				<tr>
					<td><c:out value="${sale.nft.name}" /></td>
					<td><c:out value="${sale.nft.type}" /></td>
					<td><c:out value="${sale.nft.description}" /></td>
					<td><c:out value="${sale.nft.imageUrl}" /></td>
					<td><fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${sale.creationTime}" /></td>
					<td><c:out value="${bid.bidValue}" /></td>
					<td><fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${bid.expirationTime}" /></td>
					<td><c:out value="${status}" /></td>
					<td>
						<c:if test = "${status == 'Pending'}">
							<a href="/sale/cancel/<c:out value="${sale.id}" />">
  								<span class="material-symbols-outlined">close</span>
							</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div align="center">
		<a href="/profile">Back to Profile</a><br/>
		<a href="/logout">Logout</a>
	</div>
</body>
</html>