<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>View Wallet Balance</title>
		<link href="/css/style.css" rel="stylesheet" type="text/css">
	</head>
<body>
	<div align="right">
		<a href="/profile">Back to Profile</a> | <a href="/logout">Logout</a>
	</div>
	<br>
	<div align="center">
		<table border="0" cellpadding="5" style="width:30%">
			<caption>
				<h2>Wallet Balance</h2>
			</caption>
			<thead>
				<tr>
					<th>Crypto</th>
					<th>Free balance</th>
					<th>Commited balance</th>
					<th colspan="2"></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${wallets}" var="wallet">
				<tr>
					<td><c:out value="${wallet.walletId.cryptocurrency.name}" /></td>
					<td class="center"><fmt:formatNumber type="number" maxFractionDigits="5" value="${wallet.balance}"/></td>
					<td class="center"><fmt:formatNumber type="number" maxFractionDigits="5" value="${wallet.commitedBalance}"/></td>
					<td class="center">
						<form action="withdraw" method="post">
							<input type="hidden" name="walletId" value="${wallet.walletId.user.id}" />
							<input type="hidden" name="symbol" value="${wallet.walletId.cryptocurrency.symbol}" />
							<input type="submit" value="Withdraw" />
						</form>
					</td>
					<td class="center">
						<form action="deposit" method="post">
							<input type="hidden" name="walletId" value="${wallet.walletId.user.id}" />
							<input type="hidden" name="symbol" value="${wallet.walletId.cryptocurrency.symbol}" />
							<input type="submit" value="Deposit" />
						</form>
					</td>
				</tr>
			</c:forEach>
			<tbody>
		</table><br/>
		${msg}
	</div>
</body>
</html>