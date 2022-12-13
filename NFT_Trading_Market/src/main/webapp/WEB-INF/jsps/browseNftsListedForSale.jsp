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
<link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div align="right">
		<a href="/profile">Back to Profile</a> | <a href="/logout">Logout</a>
	</div>
	</br>
	<c:choose>
		<c:when test="${sales.size() == 0}">
			<div align="center"> <h3>You haven't created any sales yet.</h3></div>
		</c:when>
		<c:otherwise>
			
			<div align="center">
				<h3><c:out value="${msg}"/></h3>
			</div>
			</br>
			<div align="center">
				<table border="0" cellpadding="5" style="width:70%">
					<caption>
						<h2>My Sales</h2>
					</caption>
					<thead>
						<tr>
							<th colspan="4">NFT</th>
							<th colspan="5">Sale</th>
							<th colspan="2">Auction</th>
						</tr>
					</thead>
					<thead>
						<tr>
							<th>Image</th>
							<th>Name</th>
							<th>Type</th>
							<th>Description</th>
							<th>Listing time</th>
					    	<th>Type</th>
					    	<th>Expected value</th>
							<th>Status</th>
							<th>Cancel</th>
							<th>Highest Offer</th>
							<th>Expiration time</th>
						</tr>
					</thead>
					<c:forEach items="${sales}" var="sale">
					
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
							<td style="vertical-align:center; text-align:center;"><img src="${sale.nft.imageUrl}" alt="${sale.nft.name}"></td>
							<td>${sale.nft.name}</td>
						    <td>${sale.nft.type}</td>
						    <td>${sale.nft.description}</td>
						    <td class="center"><fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${sale.creationTime}" /></td>
						    <td class="center">${sale.type}</td>
						    <td class="center"><fmt:formatNumber type="number" maxFractionDigits="5" value="${sale.expectedValue}"/></td>
						    <td class="center">${status}</td>
						    <td class="center">
						    	<c:choose>
									<c:when test="${status == 'Pending'}"><span class="action_btn"><a href="/sale/cancel/${sale.id}" onclick="return confirm('Do you want to cancel this sale?');">Cancel order</a></span></c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<td class="center">
								<c:choose>
									<c:when test="${bid.bidValue > 0}"><fmt:formatNumber type="number" maxFractionDigits="5" value="${bid.bidValue}"/></c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							<td class="center">
								<c:choose>
									<c:when test="${bid.bidValue > 0}"><fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${bid.expirationTime}" /></c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>