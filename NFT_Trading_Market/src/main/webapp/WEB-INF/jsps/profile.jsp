<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>User Profile</title>
		<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,300,0,200"s/>
		<style>
			
			h2 {
				margin-top: 100px;
			  	padding: 10px;
			
			}
			.center {
				margin-top: 100px;
			  	padding: 10px;
			}

			.grid-container {
			  	display: grid;
			  	grid-template-columns: auto auto auto;
			  	row-gap: 100px;
			  	margin-top: 100px;
			  	max-width: 800px;
			  	padding: 20px;
			}
			
			.card {
			  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
			  margin: auto;
			  width: 150px;
			  text-align: center;
			  font-family: arial;
			}
			
			.material-symbols-outlined {
			
				margin: 25px;
			    font-size: 50px;
			
			}
			
			.button {
			  border: none;
			  outline: 0;
			  display: inline-block;
			  padding: 6px;
			  color: white;
			  background-color: rgb(75, 163, 212);
			  text-align: center;
			  cursor: pointer;
			  width: 92%;
			  font-size: 18px;
			}
			
			.button:hover {
			  opacity: 0.7;
			}
			
			a { 
				text-decoration: none;
				text-color: white;
			}
			
		</style>
	</head>
	<body>
		
		<div align="center">
		
			<h2>Welcome back ${user}</h2>
			
			<div class="grid-container" align="center">
		
				<div class="card">
			    	<span class="material-symbols-outlined">add_photo_alternate</span>
			    	<a class="button" href="/createNft">Create NFT</a>
			  	</div>
			
			  	<div class="card">
			    	<span class="material-symbols-outlined">shopping_bag</span>
			    	<a class="button" href="/sale/listOpened">Buy NFT</a>
				</div>
			  
			  	<div class="card">
			    	<span class="material-symbols-outlined">photo_library</span>
			    	<a class="button" href="/listNFT">My NFTs</a>
			  	</div>
			  
			  	<div class="card">
			    	<span class="material-symbols-outlined">sell</span>
			    	<a class="button" href="/sale">My sales</a>
			  	</div>
			  	
			  	<div class="card">
			    	<span class="material-symbols-outlined">account_balance_wallet</span>
			    	<a class="button" href="/viewBalance">Access Wallet</a>
			  	</div>
			  	
			  	<div class="card">
			    	<span class="material-symbols-outlined">logout</span>
			    	<a class="button" href="/logout" onclick="return confirm('Do you want to logout?');">Logout</a>
			  	</div>
			
			</div>
			
		</div>
		
	<!-- 
	<pre>
	Click here to <a href="createNft">Create NFT</a><br>
	Click here to <a href="sale/listOpened">Buy NFT</a><br>
	Click here to <a href="listNFT">My NFTs</a><br>
	Click here to <a href="sale">My sales</a><br>
	Click here to <a href="viewBalance">Access Wallet</a><br>
	Click here to <a href="logout">Logout</a><br>
	</pre>
	-->
	</body>
</html>