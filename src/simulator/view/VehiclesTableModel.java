package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private String[] _colNames = { "Id", "Location", "Itinerary", "C02 class", "Max.Speed", "Speed", "Total C02",
			"Distance" };
	private List<Vehicle> _vehicles;

	VehiclesTableModel(Controller _ctrl) {
		_vehicles = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getRowCount() {

		return _vehicles.size();
	}

	@Override
	public int getColumnCount() {

		return _colNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _vehicles.get(rowIndex).getId();
			break;
		case 1:
			Vehicle v = _vehicles.get(rowIndex);
			VehicleStatus status = v.getStatus();

			if (status == VehicleStatus.PENDING)
				s = "Pending";
			else if (status == VehicleStatus.TRAVELING)
				s = v.getRoad() + ":" + v.getTotalDist();
			else if (status == VehicleStatus.WAITING)
				s = "Waiting:" + v.getRoad().getDest();
			else
				s = "Arrived";
			break;
		case 2:
			s = _vehicles.get(rowIndex).getItinerary().toString();
			break;
		case 3:
			s = _vehicles.get(rowIndex).getContClass();
			break;

		case 4:
			s = _vehicles.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = _vehicles.get(rowIndex).getSpeed();
			break;
		case 6:
			s = _vehicles.get(rowIndex).getTotalCO2();
			break;
		case 7:
			s = _vehicles.get(rowIndex).getTotalDist();
			break;
		}
		return s;
	}

	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_vehicles = map.getVehicles();
			fireTableStructureChanged();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map);

	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map);

	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map);

	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map);

	}

}
