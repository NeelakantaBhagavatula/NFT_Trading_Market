<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>User Profile</title>
  </head>
  <body>
    <h2>Hello [Username] !!!</h2>
    <pre>
	<h4> Click here to <a href="createnft"> Create NFT</a> </h4>
	<h4> Click here to <a href="Buynft">Buy NFT</a> </h4>
	<h4> Click here to <a href="sellnft">Sell NFT</a> </h4>

  ${nft.getName()}
  ${nft.getType()}
  ${nft.getDescription()}
  ${nft.getImageUrl()}
  ${nft.getAssetUrl()}
  
<a href="logout">Logout</a>
</pre>
  </body>
</html>
