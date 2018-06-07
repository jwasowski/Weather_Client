<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Klient pogodowy</title>
</head>
<body>
	<form method="get">
		Miasto: <select id="city" name="city">
			<option value="Gdansk">Gdansk</option>
			<option value="Warsaw">Warszawa</option>
			<option value="Krakow">Krakow</option>
			<option value="Wroclaw">Wroclaw</option>
			<option value="Poznan">Poznan</option>
			<option value="Lodz">Lodz</option>
			<option value="Katowice">Katowice</option>
		</select>
		Panstwo: <select id="country" name="country">
			<option value="pl">Polska</option>
		</select>
		<input type="submit" name="wyslij" value="wyslij" formaction="weather" />
		</form>
</body>
</html>