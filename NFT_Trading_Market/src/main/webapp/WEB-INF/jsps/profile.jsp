<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Profile</title>
</head>
<body>
	<h2>Welcome back ${user}</h2>
<pre>
Click here to <a href="createNft">Create NFT</a><br>
Click here to <a href="buyNft">Buy NFT</a><br>
Click here to <a href="sellNft">Sell NFT</a><br>
Click here to <a href="viewBalance">View Wallet Balance</a><br>
Click here to <a href="deposit">Deposit to Wallet</a><br>
Click here to <a href="withdraw">Withdraw from Wallet</a><br>
Click here to <a href="logout">Logout</a><br>


  ${nft.getName()}
  ${nft.getType()}
  ${nft.getDescription()}
  ${nft.getImageUrl()}
  ${nft.getAssetUrl()}
  
<a href="logout">Logout</a>
</pre>
  </body>
</html>
