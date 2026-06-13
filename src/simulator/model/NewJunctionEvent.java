package simulator.model;

public class NewJunctionEvent extends Event {
	private Junction j;

	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy,
			int xCoor, int yCoor) {
		super(time);
		j = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
	}

	@Override
	void execute(RoadMap map) {
		map.addJunction(j);
	}

	@Override
	public String toString() {
		return "New Junction '" + j.getId() + "'";
	}
}
