package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

	public NewVehicleEventBuilder() {
		super("new_vehicle", "A new vehicle");
	}

	protected void fillInData(JSONObject o) {
		o.put("time", 0);
		o.put("maxspeed", 0);
		o.put("contClass", 0);
		o.put("itinerary", 0);
	}

	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int maxSpeed = data.getInt("maxspeed");
		int contClass = data.getInt("class");

		JSONArray array = data.getJSONArray("itinerary");
		List<String> itinerary = new ArrayList<>();

		for (Object item : array) {
			itinerary.add(item.toString());
		}

		return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
	}

}
