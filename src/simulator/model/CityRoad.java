package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)
			throws IllegalArgumentException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int x;
		if (this.weather.equals(Weather.WINDY) || this.weather.equals(Weather.STORM))
			x = 10;
		else
			x = 2;
		if (this.total_cont - x >= 0)
			this.total_cont -= x;
		else
			this.total_cont = 0;

	}

	@Override
	void updateSpeedLimit() {
		// No tiene utilidad

	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return ((11 - v.getContClass()) * this.actualSpeedLimit) / 11;
	}

}
