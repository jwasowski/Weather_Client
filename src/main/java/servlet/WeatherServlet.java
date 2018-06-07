package servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.WeatherClient;
import domain.WeatherApiResponse;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String APPID = "a9ff3f93b3ff0dfe640fa7f48b87d4d1";
	private static final String units = "metric";
	private static final String lang = "pl";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Deklaracje zmiennych i powolanie klienta
		WeatherClient client = new WeatherClient();
		HttpSession session = request.getSession();
		WeatherApiResponse resp = null;
		String respString = null;
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		LocalDateTime now = LocalDateTime.now();
		// Jezeli to pierwsze zapytanie to dodaje date jako atryb sesji
		// (zapisuje wartosc o 11 minut mniejsza)
		if (session.getAttribute(city) == null) {
			session.setAttribute(city, now.minusMinutes(11));
		}
		// Pobiera date z atrybutu sesji
		LocalDateTime fromSession = (LocalDateTime) session.getAttribute(city);
		response.setContentType("text/html");
		// Porownuje date terazniejsza z data z sesji dla danego miasta (jezeli
		// roznica jest wieksza niz 10 minut to aktualizauje / dodaje nowe dane)
		if (getSeconds(now) - getSeconds(fromSession) > 600) {
			String qParam = city + "," + country;
			session.setAttribute(city, now);
			resp = client.getWeather(qParam, APPID, units, lang);
			client.addToDb(resp);
			respString = print(resp);
			respString = createGoBackButton(respString);
			response.getWriter().print(respString);
		} // W przypadku gdy nie minelo 10 minut od ostatniej aktualizacji to wyswietla dane z pamieci aplikacji
		else {
			respString = print(client.getByName(city));
			respString = createGoBackButton(respString);
			response.getWriter().print(respString);
		}

	}

	private String print(WeatherApiResponse response) {
		String returnString = "Miasto: " + response.getName() + "<br>" + "Zachmurzenie: "
				+ response.getWeather().get(0).getDescription() + "<br>" + "Temperatura: "
				+ response.getMain().getTemp() + " C" + "<br>" + "Cisnienie: " + response.getMain().getPressure()
				+ " hPa" + "<br>" + "Predkosc wiatru: " + response.getWind().getSpeed() + " km/h<br>";
		return returnString;
	}

	private String createGoBackButton(String printResponse) {
		printResponse += "<form method=\"Get\"><input type=\"submit\" name=\"goback\" value=\"Wroc\" formaction=\"index.jsp\" />";
		return printResponse;
	}

	private double getSeconds(LocalDateTime t) {
		long y = t.getYear();
		long m = t.getMonthValue();
		long d = t.getDayOfMonth();
		long h = t.getHour();
		long min = t.getMinute();
		long s = t.getSecond();
		return (y * 31556926) + (m * 2629743.83) + (d * 86400) + (h * 3600) + (min * 60) + s;
	}

}
