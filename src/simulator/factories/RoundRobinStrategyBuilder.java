package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss", "A new roundRobinStrategy");
	}

	@Override
	protected void fill_in_data(JSONObject o) {
		o.put("timeslot", 1);
	}

	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		int timeSlot;
		if (data.has("timeslot"))
			timeSlot = data.getInt("timeslot");
		else
			timeSlot = 1;
		return new RoundRobinStrategy(timeSlot);
	}

}
