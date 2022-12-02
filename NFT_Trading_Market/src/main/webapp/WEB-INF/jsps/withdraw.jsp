<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Withdraw Currency from Wallet</title>
</head>
<body>

<div align="center">
	<form action="updateBalance" method="post">
		<h2>Withdraw from Wallet :</h2>
		<input type="number" name="balanceToWithdrawOrDeposit" />
		<input type="hidden" name="walletId" value="${walletId}" />
		<input type="hidden" name="symbol" value="${symbol}" />
		<input type="hidden" name="action" value="withdraw" />
		<input type="submit" value="Withdraw" />
	</form>
</div>

</body>
</html>