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
import simulator.model.SetContClassEvent;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private List<Vehicle> vehicles;
	private int currTime;
	private JComboBox<Vehicle> vList;

	public ChangeCO2ClassDialog(Frame parent) {
		super(parent, "Change CO2 Class", true);
		vList = new JComboBox<>();
		initGUI();
	}

	public void set(Frame parent, Controller _ctrl, List<Vehicle> vehicles, int currTime) {
		this._ctrl = _ctrl;
		this.vehicles = vehicles;
		this.currTime = currTime;
		vList.removeAllItems();
		for (Vehicle v : vehicles) {
			vList.addItem(v);
		}
		vList.setSelectedIndex(0);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}

	private void initGUI() {
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel interactivePanel = new JPanel();
		interactivePanel.setLayout(new BoxLayout(interactivePanel, BoxLayout.X_AXIS));

		interactivePanel.add(Box.createHorizontalStrut(5));
		interactivePanel.add(new JLabel("Vehicle: "));
		interactivePanel.add(vList);
		interactivePanel.add(Box.createHorizontalStrut(5));

		JComboBox<Integer> co2List = new JComboBox<>();
		for (int i = 0; i <= 10; i++) {
			co2List.addItem(i);
		}
		interactivePanel.add(new JLabel("CO2 Class: "));
		interactivePanel.add(co2List);
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
				List<Pair<String, Integer>> si = new ArrayList<>();
				si.add(new Pair<>(vList.getSelectedItem().toString(), (int) co2List.getSelectedItem()));
				_ctrl.addEvent(new SetContClassEvent((int) spinnerTicks.getValue() + currTime, si));
				setVisible(false);
			}
		});

		panelButtons.add(cancelButton);
		panelButtons.add(Box.createHorizontalStrut(10));
		panelButtons.add(okButton);

		JLabel message = new JLabel(
				"Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.");
		message.setAlignmentX(CENTER_ALIGNMENT);
		this.add(message);
		this.add(Box.createVerticalStrut(10));
		this.add(interactivePanel);
		this.add((Box.createVerticalStrut(10)));
		this.add(panelButtons);
		this.add(Box.createVerticalStrut(10));
		this.pack();
	}

}
