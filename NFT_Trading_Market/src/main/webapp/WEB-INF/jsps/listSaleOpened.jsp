<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>NFT's Listed for Sale</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20,300,0,0" />
<link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div align="right">
		<a href="/profile">Back to Profile</a> | <a href="/logout">Logout</a>
	</div>
	<br>
	<c:choose>
		<c:when test="${sales.size() == 0}">
			<div align="center"> <h3>There are no sales available at this time.</h3></div>
		</c:when>
		<c:otherwise>
		
			<div align="center">
				<table border="0" cellpadding="5" style="width:70%">
					<caption>
						<h2>List of Opened Sales</h2>
						<h3>${msg}</h3>
					</caption>
					<thead>
						<tr>
					    	<th colspan="4">NFT</th>
					    	<th colspan="5">Sale</th>
					  	</tr>
					</thead>
					<thead>
						<tr>
					    	<th></th>
					    	<th>Name</th>
					    	<th>Type</th>
					    	<th>Description</th>
					    	<th>Listing time</th>
					    	<th>Type</th>
					    	<th>Price / Highest offer</th>
					    	<th>My offer</th>
					    	<th></th>
					  	</tr>
					</thead>
					
					<jsp:useBean id="now" class="java.util.Date"/>
					
					<c:forEach items="${sales}" var="sale">
					
						<c:forEach items="${sale.bids}" var="bid">
						
							<c:if test = "${bid.user.id == userId && bid.expirationTime gt now}">
								<c:set var = "bidId" value = "${bid.id}"/>
								<c:set var = "bidValue" value = "${bid.bidValue}"/>
							</c:if>
							
							<c:if test = "${ bid.expirationTime gt now}">
								<c:set var = "highestBidValue" value = "${bid.bidValue}"/>
								<c:set var = "highestBidId" value = "${bid.user.id}"/>
							</c:if>
						
						</c:forEach>
						
						<c:choose>
							<c:when test="${highestBidValue > 0}">
								<c:set var = "expectedValue" value = "${highestBidValue}"/>
							</c:when>    
							<c:otherwise>
								<c:set var = "expectedValue" value = "${sale.expectedValue}"/>
							</c:otherwise>
						</c:choose>
						
					<tbody>
						<tr>
							<td rowspan="2" style="vertical-align:center; text-align:center;"><img src="${sale.nft.imageUrl}" alt="${sale.nft.name}"></td>
						    <td>${sale.nft.name}</td>
						    <td>${sale.nft.type}</td>
						    <td>${sale.nft.description}</td>
						    <td class="center"><fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${sale.creationTime}" /></td>
						    <td class="center camel">${sale.type}</td>
						    <td class="center"><fmt:formatNumber type="number" maxFractionDigits="5" value="${expectedValue}"/></td>
						    <td class="center">
						    <c:choose>
							    <c:when test="${bidValue > 0}"><fmt:formatNumber type="number" maxFractionDigits="5" value="${bidValue}"/></c:when>    
							    <c:otherwise>-</c:otherwise>
							</c:choose>
							</td>
						    <td><span class="action_btn"><a href="/sale/makeOffer/${sale.id}">Buy NFT</a></span></td>
						</tr>
						<tr>
						<c:choose>
							<c:when test="${ highestBidId == userId }">
						    	<td colspan="8" class="message">You have made the highest bid so far.</td>
						    </c:when>
						    <c:when test="${bidValue > 0}">
						    	<td colspan="7" class="message">You no longer have the highest bidder.</td>
						    	<td><span class="action_btn"><a href="/sale/cancel/bid/${bidId}">Cancel offer</a></span></td>
						    </c:when>  
						    <c:otherwise>
						        <td colspan="9"></td>
						    </c:otherwise>
						</c:choose>
						</tr>
					</tbody>
						
					</c:forEach>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>