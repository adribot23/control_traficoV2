package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private String[] _colNames = { "Id", "Length", "Weather", "Max.Speed", "Speed Limit", "Total C02", "CO2 Limit" };
	private List<Road> _roads;

	RoadsTableModel(Controller _ctrl) {
		_roads = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getRowCount() {

		return _roads.size();
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
			s = _roads.get(rowIndex).getId();
			break;
		case 1:
			s = _roads.get(rowIndex).getLength();
			break;
		case 2:
			s = _roads.get(rowIndex).getWeather().toString();
			break;
		case 3:
			s = _roads.get(rowIndex).getMaxSpeed();
			break;
		case 4:
			s = _roads.get(rowIndex).getSpeedLimit();
			break;
		case 5:
			s = _roads.get(rowIndex).getTotalCO2();
			break;
		case 6:
			s = _roads.get(rowIndex).getContLimit();
			break;
		}
		return s;
	}

	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_roads = map.getRoads();
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
