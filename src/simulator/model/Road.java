package simulator.model;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
	protected Junction srcJunc;
	protected Junction destJunc;
	protected int length;
	protected int maxSpeed;
	protected int actualSpeedLimit;
	protected int contLimit; // lı́mite de contaminación
	protected Weather weather;
	protected int total_cont; // Total contamination
	protected List<Vehicle> vehicles;
	private VehicleComparator v;

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)
			throws IllegalArgumentException {
		super(id);
		if (maxSpeed > 0 && contLimit >= 0 && length > 0 && srcJunc != null && destJunc != null && weather != null) {
			this.srcJunc = srcJunc;
			this.destJunc = destJunc;
			this.maxSpeed = maxSpeed;
			this.actualSpeedLimit = maxSpeed;
			this.contLimit = contLimit;
			this.length = length;
			this.weather = weather;
			this.total_cont = 0;
			this.v = new VehicleComparator();
			vehicles = new ArrayList<>();
			this.destJunc.addIncommingRoad(this);
			this.srcJunc.addOutGoingRoad(this);

		} else
			throw new IllegalArgumentException("Error 404"); // Cambiar excpecion: tiene que decir lo que ha fallado
	}

	void enter(Vehicle v) throws IllegalArgumentException {
		if (v.getLocation() == 0 && v.getSpeed() == 0)
			vehicles.add(v);
		else
			throw new IllegalArgumentException(
					"ERROR: la localización del vehı́culo no es 0 o que la velocidad del vehı́culo no es 0");
	}

	void exit(Vehicle v) {
		vehicles.remove(v);
	}

	void setWeather(Weather w) throws IllegalArgumentException {
		if (w != null)
			this.weather = w;
		else
			throw new IllegalArgumentException("ERROR: weather es null");
	}

	void addContamination(int c) {
		if (c >= 0)
			this.total_cont += c;
		else
			throw new IllegalArgumentException("ERROR: la contaminación es negativa");

	}

	abstract void reduceTotalContamination();

	abstract void updateSpeedLimit();

	abstract int calculateVehicleSpeed(Vehicle v);

	@Override
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for (Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		Collections.sort(this.vehicles, v);
	}

	@Override
	public JSONObject report() {
		JSONObject report = new JSONObject();
		report.put("id", this._id);
		report.put("speedlimit", this.actualSpeedLimit);
		report.put("weather", this.weather.toString());
		report.put("co2", this.total_cont);

		JSONArray vehiclesArray = new JSONArray();
		for (Vehicle v : getVehicles()) {
			vehiclesArray.put(v.getId());
		}

		report.put("vehicles", vehiclesArray);
		return report;
	}

	public int getLength() {
		return this.length;
	}

	public Junction getDest() {
		return this.destJunc;
	}

	public Junction getSrc() {
		return this.srcJunc;
	}

	public Weather getWeather() {
		return this.weather;
	}

	public int getContLimit() {
		return this.contLimit;
	}

	public int getMaxSpeed() {

		return this.maxSpeed;
	}

	public int getTotalCO2() {
		return this.total_cont;
	}

	public int getSpeedLimit() {
		return this.actualSpeedLimit;
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicles);
	}

}
