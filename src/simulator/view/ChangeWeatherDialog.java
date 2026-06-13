package simulator.view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private List<Road> roads;
	private int currTime;
	JComboBox<Road> rList;

	public ChangeWeatherDialog(Frame parent) {
		super(parent, "Change Road Weather", true);
		rList = new JComboBox<>();
		initGUI();
	}

	public void set(Frame parent, Controller _ctrl, List<Road> roads, int currTime) {
		this._ctrl = _ctrl;
		this.roads = roads;
		this.currTime = currTime;
		rList.removeAllItems();
		for (Road v : roads) {
			rList.addItem(v);
		}
		rList.setSelectedIndex(0);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

	private void initGUI() {
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel interactivePanel = new JPanel();
		interactivePanel.setLayout(new BoxLayout(interactivePanel, BoxLayout.X_AXIS));


		interactivePanel.add(Box.createHorizontalStrut(5));
		interactivePanel.add(new JLabel("Road: "));
		interactivePanel.add(rList);
		interactivePanel.add(Box.createHorizontalStrut(5));

		JComboBox<Weather> weatherList = new JComboBox<>(Weather.values());

		interactivePanel.add(new JLabel("Weather: "));
		interactivePanel.add(weatherList);
		interactivePanel.add(Box.createHorizontalStrut(5));

		JSpinner spinnerTicks = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		interactivePanel.add(new JLabel("Ticks: "));
		interactivePanel.add(spinnerTicks);
		interactivePanel.add(Box.createHorizontalStrut(5));

		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> setVisible(false));

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				List<Pair<String, Weather>> si = new ArrayList<>();
				si.add(new Pair<>(rList.getSelectedItem().toString(), (Weather) weatherList.getSelectedItem()));
				_ctrl.addEvent(new SetWeatherEvent((int) spinnerTicks.getValue() + currTime, si));
				setVisible(false);
			}
		});

		panelButtons.add(cancelButton);
		panelButtons.add(Box.createHorizontalStrut(10));
		panelButtons.add(okButton);

		JLabel message = new JLabel(
				"Schedule an event to change the weather of a road after a given number of simulation ticks from now.");
		message.setAlignmentX(CENTER_ALIGNMENT);
		this.add(message);
		this.add(Box.createVerticalStrut(10));
		this.add(interactivePanel);
		this.add(Box.createVerticalStrut(10));
		this.add(panelButtons);
		this.add(Box.createVerticalStrut(10));
		this.pack();

	}

}
