package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> junctionList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String, Junction> junctionMap;
	private Map<String, Road> roadMap;
	private Map<String, Vehicle> vehicleMap;

	RoadMap() {
		junctionList = new ArrayList<>();
		roadList = new ArrayList<>();
		vehicleList = new ArrayList<>();
		junctionMap = new HashMap<>();
		roadMap = new HashMap<>();
		vehicleMap = new HashMap<>();
	}

	void addJunction(Junction j) {
		if (!junctionMap.containsKey(j.getId())) {
			junctionList.add(j);
			junctionMap.put(j.getId(), j);

		} else
			throw new IllegalArgumentException("ERROR: another junction with the same ID already exists");

	}

	void addRoad(Road r) {
		if (!roadMap.containsKey(r.getId())) {
			if (junctionMap.containsKey(r.getDest().getId()) && junctionMap.containsKey(r.getSrc().getId())) {
				roadList.add(r);
				roadMap.put(r.getId(), r);
			} else
				throw new IllegalArgumentException(
						"ERROR: the source or destination junction of r is not in the roadmap");
		} else
			throw new IllegalArgumentException("ERROR: another road with the same ID already exists");
	}

	void addVehicle(Vehicle v) {
		if (!vehicleMap.containsKey(v.getId())) {
			List<Junction> itinerary = v.getItinerary();
			Junction j, jNext;
			Road r;
			for (int i = 0; i < itinerary.size() - 1; i++) {
				j = itinerary.get(i);
				jNext = itinerary.get(i + 1);
				r = j.roadTo(jNext);
				if (r == null || !roadMap.containsKey(r.getId()))
					throw new IllegalArgumentException("ERROR: the itinerary is not valid");
			}
			vehicleList.add(v);
			vehicleMap.put(v.getId(), v);

		} else
			throw new IllegalArgumentException("ERROR: another vehicle with the same ID already exists");

	}

	public Junction getJunction(String id) {
		return junctionMap.getOrDefault(id, null);
	}

	public Road getRoad(String id) {
		return roadMap.getOrDefault(id, null);
	}

	public Vehicle getVehicle(String id) {
		return vehicleMap.getOrDefault(id, null);
	}

	public List<Junction> getJunctions() {
		return Collections.unmodifiableList(junctionList);
	}

	public List<Road> getRoads() {
		return Collections.unmodifiableList(roadList);
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicleList);
	}

	void reset() {
		junctionList.clear();
		roadList.clear();
		vehicleList.clear();
		junctionMap.clear();
		roadMap.clear();
		vehicleMap.clear();
	}

	public JSONObject report() {
		JSONObject report = new JSONObject();
		JSONArray array = new JSONArray();
		for (Junction j : junctionList) {
			array.put(j.report());
		}
		report.put("junctions", array);

		JSONArray array1 = new JSONArray();
		for (Road j : roadList) {
			array1.put(j.report());
		}
		report.put("roads", array1);

		JSONArray array2 = new JSONArray();
		for (Vehicle j : vehicleList) {
			array2.put(j.report());
		}
		report.put("vehicles", array2);

		return report;
	}
}
