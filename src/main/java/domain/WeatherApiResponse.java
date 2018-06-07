package domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "coord", "base", "sys", "clouds", "visibility", "dt", "id", "cod" })
public class WeatherApiResponse {

	private List<Weather> weather;
	private AtmospehricParams main;
	private Wind wind;

	private String name;

	public List<Weather> getWeather() {
		return weather;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public AtmospehricParams getMain() {
		return main;
	}

	public void setMain(AtmospehricParams main) {
		this.main = main;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
