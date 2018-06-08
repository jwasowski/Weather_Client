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
	private static final String APPID = "4928b50bff75b71a7ac2fc11f8477ccd";
	private static final String units = "metric";
	private static final String lang = "pl";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		WeatherClient client = new WeatherClient();
		HttpSession session = request.getSession();
		String responseAsString = null;
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		LocalDateTime now = LocalDateTime.now();
		sessionAttributeCheck(session, city, now);
		LocalDateTime timeFromSession = (LocalDateTime) session.getAttribute(city);
		response.setContentType("text/html");
		if (transformToSeconds(now) - transformToSeconds(timeFromSession) > 7200) {
			String queryParam = city + "," + country;
			session.setAttribute(city, now);
			responseAsString = getNewData(queryParam, client, responseAsString, now);
			response.getWriter().print(responseAsString);
		} else {
			responseAsString = printAsString(client.getByName(city));
			responseAsString = createGoBackButton(responseAsString);
			response.getWriter().print(responseAsString);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.sendRedirect("/restapp/index.jsp");
	}

	private String getNewData(String queryParam, WeatherClient client, String responseAsString, LocalDateTime now) {
		WeatherApiResponse resp = client.getWeather(queryParam, APPID, units, lang);
		client.addToDb(resp);
		responseAsString = printAsString(resp);
		responseAsString = createGoBackButton(responseAsString);
		return responseAsString;
	}

	private void sessionAttributeCheck(HttpSession session, String city, LocalDateTime now) {
		if (session.getAttribute(city) == null) {
			session.setAttribute(city, now.minusMinutes(121));
		}
	}

	private String printAsString(WeatherApiResponse response) {
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

	private double transformToSeconds(LocalDateTime t) {
		long y = t.getYear();
		long m = t.getMonthValue();
		long d = t.getDayOfMonth();
		long h = t.getHour();
		long min = t.getMinute();
		long s = t.getSecond();
		return (y * 31556926) + (m * 2629743.83) + (d * 86400) + (h * 3600) + (min * 60) + s;
	}

}
