package simulator.factories;

import org.json.JSONObject;

import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road", "A new inter city road");
	}

	@Override
	protected NewInterCityRoadEvent create_instance(JSONObject data) {
		initialize(data);
		return new NewInterCityRoadEvent(time, id, src, dest, length, co2limit, maxSpeed, weather);
	}

}
