package domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "coord", "base", "sys", "clouds", "visibility", "dt", "id", "cod" })
public class WeatherApiResponse {

	private List<WeatherParams> weather;
	private AtmospehricParams main;
	private WindParams wind;
	private String name;

	public List<WeatherParams> getWeather() {
		return weather;
	}

	public void setWeather(List<WeatherParams> weather) {
		this.weather = weather;
	}

	public AtmospehricParams getMain() {
		return main;
	}

	public void setMain(AtmospehricParams main) {
		this.main = main;
	}

	public WindParams getWind() {
		return wind;
	}

	public void setWind(WindParams wind) {
		this.wind = wind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
