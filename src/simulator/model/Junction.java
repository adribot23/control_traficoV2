package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	private List<Road> incomingRoads;
	private List<List<Vehicle>> queues;
	private Map<Junction, Road> outgoingRoads;
	private Map<Road, List<Vehicle>> _queueByRoad;
	private int greenLightIndex;
	private int lastSwitchingTime;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int x;
	private int y;

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor)
			throws IllegalArgumentException {
		super(id);
		if (lsStrategy != null && dqStrategy != null && xCoor >= 0 && yCoor >= 0) {

			this.lastSwitchingTime = 0;
			this.greenLightIndex = -1;
			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			this.x = xCoor;
			this.y = yCoor;

			this.incomingRoads = new ArrayList<>();
			this.outgoingRoads = new HashMap<>();
			this.queues = new ArrayList<>();
			this._queueByRoad = new HashMap<>();
		} else
			throw new IllegalArgumentException("Error 404"); // Cambiar excpecion: tiene que decir lo que ha fallado
	}

	void addIncommingRoad(Road r) throws IllegalArgumentException {
		if (r.getDest().equals(this)) {
			this.incomingRoads.add(r);
			List<Vehicle> v = new ArrayList<>();
			this.queues.add(v);
			this._queueByRoad.put(r, v);
		} else
			throw new IllegalArgumentException("ERROR: the destJunction of the road is not equal to this junction");
	}

	void addOutGoingRoad(Road r) throws IllegalArgumentException {
		if (r.getSrc().equals(this))
			this.outgoingRoads.put(r.getDest(), r);
		else
			throw new IllegalArgumentException("ERROR: the destJunction of the road is not equal to this junction");
	}

	void enter(Vehicle v) {
		this._queueByRoad.get(v.getRoad()).add(v);
	}

	Road roadTo(Junction j) {
		return this.outgoingRoads.get(j);
	}

	@Override
	void advance(int time) {
		if (this.greenLightIndex != -1) {
			List<Vehicle> q = _queueByRoad.get(incomingRoads.get(greenLightIndex));
			List<Vehicle> v = this.dqStrategy.dequeue(q);
			for (Vehicle ve : v) {
				ve.moveToNextRoad();
				q.remove(ve);
			}
		}
		int green = this.lsStrategy.chooseNextGreen(incomingRoads, queues, greenLightIndex, lastSwitchingTime, time);
		if (this.greenLightIndex != green) {
			this.greenLightIndex = green;
			this.lastSwitchingTime = time;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject report = new JSONObject();
		report.put("id", this._id);
		if (this.greenLightIndex == -1)
			report.put("green", "none");
		else
			report.put("green", this.incomingRoads.get(greenLightIndex).getId());

		JSONArray roadsArray = new JSONArray();

		for (Road r : incomingRoads) {
			JSONObject report1 = new JSONObject();

			report1.put("road", r.getId());
			JSONArray vehiclesArray = new JSONArray();

			for (Vehicle v : _queueByRoad.get(r)) {
				vehiclesArray.put(v.getId());
			}
			report1.put("vehicles", vehiclesArray);
			roadsArray.put(report1);
		}

		report.put("queues", roadsArray);
		return report;
	}

	public int getGreenLightIndex() {
		return greenLightIndex;
	}

	public List<Road> getIncomingRoads() {
		return incomingRoads;
	}

	public Map<Road, List<Vehicle>> getQueueByRoad() {
		return _queueByRoad;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
