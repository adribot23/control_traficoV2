package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather", "A new set weather event");

	}

	@Override
	protected SetWeatherEvent create_instance(JSONObject data) {

		int time = data.getInt("time");
		List<Pair<String, Weather>> sw = new ArrayList<>();

		JSONArray array = data.getJSONArray("info");
		JSONObject o;
		for (int i = 0; i < array.length(); i++) {
			o = array.getJSONObject(i);
			sw.add(new Pair<>(o.getString("road"), Weather.valueOf(o.getString("weather").toUpperCase())));

		}

		/*
		 * JSONObject o; for(Object obj: array) { o = (JSONObject) obj; sw.add(new
		 * Pair<>(o.getString("road"),
		 * Weather.valueOf(o.getString("weather").toUpperCase()))); }
		 */

		return new SetWeatherEvent(time, sw);
	}
}
