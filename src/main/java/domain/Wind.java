package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "deg"})
public class Wind {
private Float speed;

public Float getSpeed() {
	return speed;
}

public void setSpeed(Float speed) {
	this.speed = speed;
}
}
