package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	private int timeSlot;

	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if (roads.isEmpty())
			return -1;

		if (currGreen == -1) {
			int actual = 0;
			for (int i = 1; i < qs.size(); i++)
				if (qs.get(i).size() > qs.get(actual).size())
					actual = i;
			return actual;
		}

		if (currTime - lastSwitchingTime < timeSlot)
			return currGreen;

		int actual = (currGreen + 1) % roads.size();
		int actual_ant = currGreen;

		for (int i = actual; i != actual_ant; i = (i + 1) % roads.size()) {
			if (qs.get(i).size() > qs.get(actual).size())
				actual = i;
		}
		return actual;

	}
}
