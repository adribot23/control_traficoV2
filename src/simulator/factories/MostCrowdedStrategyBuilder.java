package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss", "A mostCrowdedStrategy");
	}

	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		int timeSlot;
		if (data.has("timeslot")) {
			timeSlot = data.getInt("timeslot");
		} else
			timeSlot = 1;
		return new MostCrowdedStrategy(timeSlot);
	}

	@Override
	protected void fill_in_data(JSONObject o) {
		o.put("timeslot", 1);
	}

}
