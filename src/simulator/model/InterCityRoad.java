package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws IllegalArgumentException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);

	}

	@Override
	void reduceTotalContamination() {
		int x;
		switch (this.weather) {
		case SUNNY:
			x = 2;
			break;
		case CLOUDY:
			x = 3;
		case RAINY:
			x = 10;
		case WINDY:
			x = 15;
		default:
			x = 20;
		}
		this.total_cont = ((100 - x) * this.total_cont) / 100;
	}

	@Override
	void updateSpeedLimit() {
		if (this.total_cont > this.contLimit) {
			this.actualSpeedLimit = this.maxSpeed / 2;
		} else {
			this.actualSpeedLimit = this.maxSpeed;
		}

	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {

		if (this.weather.equals(Weather.STORM)) {
			return (this.actualSpeedLimit * 8) / 10;
		}
		return this.actualSpeedLimit;
	}

}
