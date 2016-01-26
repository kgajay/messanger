<#-- @ftlvariable name="" type="com.example.views.PeopleView" -->
<html>
	<head>
		<title>DROPWIZARD</title>
	</head>
    <body>
    	<p> This is view rendering </p>
        <!-- calls getPerson().getFullName() and sanitizes it -->
        <h1>Hello, ${person.fullName?html}!</h1>
</html>