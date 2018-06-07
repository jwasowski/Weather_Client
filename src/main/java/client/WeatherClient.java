package client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.WeatherApiResponse;

public class WeatherClient {
	public static final String URL = "http://api.openweathermap.org/data/2.5/weather";
	//Wszystkie instancje tej klasy obsluguje jeden klient
	private static Client client = ClientBuilder.newClient();
	//Pamiec aplikacji
	private static List<WeatherApiResponse> db = new ArrayList<WeatherApiResponse>();
	
	//Sparametryzowane zapytanie klienta (pyta api pogodowe)
	public WeatherApiResponse getWeather(String q, String appId, String units, String lang) {
		Response response = client.target(URL).queryParam("q", q).queryParam("APPID", appId).queryParam("units", units)
				.queryParam("lang", lang).request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus() == 200)
			return response.readEntity(WeatherApiResponse.class);
		return null;
	}

	public void addToDb(WeatherApiResponse w) {
		if (getByName(w.getName()) == null)
			db.add(w);
		else
			update(w);
	}

	public WeatherApiResponse getByName(String name) {
		for (WeatherApiResponse resp : db) {
			if (resp.getName().equals(name))
				return resp;
		}
		return null;
	}
	
	public void update(WeatherApiResponse w) {
		for (WeatherApiResponse m : db) {
			if (m.getName().equals(w.getName())) {
				m.setName(w.getName());
				m.setMain(w.getMain());
				m.setWeather(w.getWeather());
				m.setWind(w.getWind());
			}
		}
	}
}
