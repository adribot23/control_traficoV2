package simulator.factories;

import org.json.JSONObject;

import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewCityRoadEventBuilder() {
		super("new_city_road", "A new city road");
	}

	@Override
	protected NewCityRoadEvent create_instance(JSONObject data) {
		super.initialize(data);
		return new NewCityRoadEvent(time, id, src, dest, length, co2limit, maxSpeed, weather);
	}

}
