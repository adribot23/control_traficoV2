package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {

	private List<Pair<String, Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if (cs != null)
			this.cs = cs;
		else
			throw new IllegalArgumentException("ERROR: cs is null");
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Integer> pair : this.cs) {
			Vehicle v = map.getVehicle(pair.getFirst());
			if (v != null)
				v.setContClass(pair.getSecond());
			else
				throw new IllegalArgumentException("ERROR: the vehicle does not exists in the map");
		}
	}

	@Override
	public String toString() {
		return "Change C02 class: " + cs;
	}

}
