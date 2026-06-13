package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private String[] _colNames = { "Id", "Green", "Queues" };
	private List<Junction> _junctions;

	JunctionsTableModel(Controller _ctrl) {
		_junctions = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getRowCount() {

		return _junctions.size();
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
			s = _junctions.get(rowIndex).getId();
			break;
		case 1:
			Junction j = _junctions.get(rowIndex);

			if (j.getGreenLightIndex() == -1)
				s = "None";
			else
				s = j.getIncomingRoads().get(j.getGreenLightIndex()).getId();
			break;

		case 2:
			s = "";
			Junction j1 = _junctions.get(rowIndex);
			Map<Road, List<Vehicle>> m = j1.getQueueByRoad();
			for (Road r : j1.getIncomingRoads()) {
				s += r.getId() + ":" + m.get(r) + " ";
			}
			break;
		}
		return s;
	}

	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_junctions = map.getJunctions();
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
