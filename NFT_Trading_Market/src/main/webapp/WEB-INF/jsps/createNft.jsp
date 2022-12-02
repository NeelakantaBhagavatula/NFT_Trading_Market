<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>NFT Creation</title>
</head>
<body>
	<h2>. Enter details to Create NFT</h2>

<form action="createnft" method="post">
<pre>
Name : <input type="text" name="name" />
Type : <input type="text" name="type" />
Description : <input type="text" name="description" />
Image URL : <input type="text" name="ImageUrl" />
Asset URL : <input type="text" name="assetUrl" />
<input type="submit" value="Create" />
${msg}
</pre>
</form>
</body>
</html>

