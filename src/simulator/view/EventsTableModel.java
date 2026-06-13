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

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private String[] _colNames = { "Time", "Desc." };
	private List<Event> _events;

	EventsTableModel(Controller _ctrl) {
		this._events = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getRowCount() {

		return _events.size();
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
			s = _events.get(rowIndex).getTime();
			break;
		case 1:
			s = _events.get(rowIndex).toString();
			break;
		}
		return s;
	}

	public void update(Collection<Event> events) {
		SwingUtilities.invokeLater(() -> {
			this._events = new ArrayList<>(events);
			this._events.sort((e1, e2) -> Integer.compare(e1.getTime(), e2.getTime()));
			fireTableStructureChanged();
		});
	}

	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(events);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(events);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(events);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(events);
	}

}
