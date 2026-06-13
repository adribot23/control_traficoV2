package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	List<Pair<String, Weather>> ws;

	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		if (ws != null)
			this.ws = ws;
		else
			throw new IllegalArgumentException("ERROR: ws is null");

	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Weather> pair : this.ws) {
			Road r = map.getRoad(pair.getFirst());
			if (r != null) {
				r.setWeather(pair.getSecond());
			} else
				throw new IllegalArgumentException("ERROR: the road does not exists in the map");
		}

	}

	@Override
	public String toString() {
		return "Change Weather: " + ws;
	}

}
