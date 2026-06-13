package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction", "A new junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	protected NewJunctionEvent create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");

		JSONArray array = data.getJSONArray("coor");
		int x = array.getInt(0);
		int y = array.getInt(1);

		LightSwitchingStrategy lss = this.lssFactory.create_instance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy ds = this.dqsFactory.create_instance(data.getJSONObject("dq_strategy"));

		return new NewJunctionEvent(time, id, lss, ds, x, y);
	}

}
