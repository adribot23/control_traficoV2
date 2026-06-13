package simulator.view;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private JLabel timeLabel;
	private JLabel eventsLabel;

	public StatusBar(Controller _ctrl) {
		initGui();
		_ctrl.addObserver(this);
	}

	private void initGui() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		timeLabel = new JLabel("Time: " + 0);
		this.add(timeLabel);

		this.add(Box.createRigidArea(new Dimension(20, 0)));

		eventsLabel = new JLabel("");

		this.add(eventsLabel);

	}

	public void update(Event e, int time) {
		SwingUtilities.invokeLater(() -> {
			if (e == null)
				eventsLabel.setText("");
			else
				eventsLabel.setText("Event added (" + e.toString() + ")");
			timeLabel.setText("Time: " + time);
			repaint();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(null, time);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(e, time);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(null, time);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(null, time);
	}

}
