package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private RoadMap map;
	private int time;
	private boolean _stopped;
	private List<JButton> buttons;
	private ChangeWeatherDialog weatherDialog;
	private ChangeCO2ClassDialog co2ClassDialog;

	ControlPanel(Controller ctrl) {
		this._ctrl = ctrl;
		_stopped = true;
		buttons = new ArrayList<>();
		co2ClassDialog = new ChangeCO2ClassDialog(getAncestor());
		weatherDialog = new ChangeWeatherDialog(getAncestor());
		initGui();
		this._ctrl.addObserver(this);

	}

	private void initGui() {
		this.setLayout(new BorderLayout());
		JToolBar toolBar = new JToolBar();

		JButton fileButton = new JButton(new ImageIcon("resources/icons/open.png"));

		fileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("resources/examples"));
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();

					try (FileInputStream input = new FileInputStream(file)) {

						_ctrl.reset();
						_ctrl.loadEvents(input);
					}

					catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(getAncestor(), ex.getMessage());

					} catch (FileNotFoundException f) {

						JOptionPane.showMessageDialog(getAncestor(), f.getMessage());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(getAncestor(), "Error inesperado: " + ex.getMessage(), "error",
								JOptionPane.ERROR_MESSAGE);
					}

				}

			}

		});

		JButton co2Button = new JButton(new ImageIcon("resources/icons/co2class.png"));
		co2Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					co2ClassDialog.set(getAncestor(), _ctrl, map.getVehicles(), time);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(getAncestor(), "ERROR: no events loaded");
				}
			}
		});

		JButton weatherButton = new JButton(new ImageIcon("resources/icons/weather.png"));
		weatherButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					weatherDialog.set(getAncestor(), _ctrl, map.getRoads(), time);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(getAncestor(), "ERROR: no events loaded");
				}
			}

		});

		JSpinner spinnerTicks = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));

		JButton runButton = new JButton(new ImageIcon("resources/icons/run.png"));
		runButton.addActionListener(e -> {
			_stopped = false;
			enableToolbar();
			run_sim((int) spinnerTicks.getValue());

		});

		JButton stopButton = new JButton(new ImageIcon("resources/icons/stop.png"));
		stopButton.addActionListener(e -> {
			_stopped = true;
		});

		JButton exitButton = new JButton(new ImageIcon("resources/icons/exit.png"));

		exitButton.addActionListener(e -> {
			int response = JOptionPane.showConfirmDialog(getAncestor(), "Do you want to exit the program?", "Exit",
					JOptionPane.YES_NO_OPTION);

			if (response == JOptionPane.YES_OPTION)
				System.exit(0);

		});

		toolBar.add(fileButton);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(co2Button);
		toolBar.add(weatherButton);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(runButton);
		toolBar.add(stopButton);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(new JLabel("Ticks:"));
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(spinnerTicks);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(exitButton);

		buttons.add(fileButton);
		buttons.add(co2Button);
		buttons.add(weatherButton);
		buttons.add(runButton);
		buttons.add(exitButton);

		this.add(toolBar, BorderLayout.CENTER);
	}

	private void enableToolbar() {
		for (JButton b : buttons)
			b.setEnabled(this._stopped);
	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
				SwingUtilities.invokeLater(() -> run_sim(n - 1));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(getAncestor(), "Error at execute: "+ e.getMessage());

				_stopped = true;
				enableToolbar();
			}
		} else {
			_stopped = true;
			enableToolbar();
		}
	}

	private JFrame getAncestor() {
		return (JFrame) SwingUtilities.getWindowAncestor(this);
	}

	public void update(RoadMap map, int time) {
		SwingUtilities.invokeLater(() -> {
			this.map = map;
			this.time = time;
			repaint();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map, time);

	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map, time);

	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map, time);

	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map, time);
	}

}
