package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class", "A new set cont class");
	}

	@Override
	protected SetContClassEvent create_instance(JSONObject data) {
		int time = data.getInt("time");
		List<Pair<String, Integer>> si = new ArrayList<>();

		JSONArray array = data.getJSONArray("info");
		JSONObject o;

		for (int i = 0; i < array.length(); i++) {
			o = array.getJSONObject(i);
			si.add(new Pair<>(o.getString("vehicle"), o.getInt("class")));

		}
		return new SetContClassEvent(time, si);

	}
}
