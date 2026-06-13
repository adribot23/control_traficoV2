package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator _sim;
	private Factory<Event> _eventsFactory;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) throws IllegalArgumentException {
		if (sim != null && eventsFactory != null) {
			_sim = sim;
			_eventsFactory = eventsFactory;
		} else
			throw new IllegalArgumentException("ERROR: sim or eventsFactory is null");
	}

	public void loadEvents(InputStream in) throws IllegalArgumentException {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		if (jo.length() == 1 && jo.has("events")) {
			JSONArray array = jo.getJSONArray("events");
			JSONObject o;
			Event ev;
			for (int i = 0; i < array.length(); i++) {
				o = array.getJSONObject(i);
				ev = _eventsFactory.create_instance(o);
				_sim.addEvent(ev);
			}
		} else
			throw new IllegalArgumentException("ERROR: the input is incorrect");
	}

	public void run(int n, OutputStream out) {

		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("  \"states\": [");
		for (int i = 0; i < n - 1; i++) {
			_sim.advance();
			p.print(_sim.report());
			p.println(",");
		}

		// last step, only if 'n > 0'
		if (n > 0) {
			_sim.advance();
			p.println(_sim.report());
		}
		p.println("]");
		p.println("}");

	}

	public void reset() {
		this._sim.reset();
	}

	public void addObserver(TrafficSimObserver o) {
		this._sim.addObserver(o);
	}

	public void removeObserver(TrafficSimObserver o) {
		this._sim.removeObserver(o);
	}

	public void addEvent(Event e) {
		this._sim.addEvent(e);
	}

	public void run(int n) {
		for (int i = 0; i < n; i++)
			this._sim.advance();
	}
}
