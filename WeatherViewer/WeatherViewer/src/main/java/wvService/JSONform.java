package wvService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import wvAPI.APIOpenWeather;
import wvList.*;
import wvUtilità.JSONoperator;
import wvUtilità.Statistiche;
import wvUtilità.UnixConverter;

@Component
public class JSONform {

	private DataList storico = new DataList();
	private Map<Long, Long> storyMap = new HashMap<Long, Long>();
	private DataList previsioni = new DataList();
	private String città;

	UnixConverter conv = new UnixConverter();
	JSONoperator oper = new JSONoperator();
	APIOpenWeather API;

	public JSONform(String città) {
		this.città = città;
		API = new APIOpenWeather(città);

	}

	public String getNome() {
		return città;
	}

	public DataList getStorico() {
		return this.storico;
	}

	public DataList getPrevisioni() {
		return this.previsioni;
	}

	public void save() {
		oper.writeJSONArrey(this.storico.getList(), "JSONdati/" + città + "Storico");
		oper.writeJSONArrey(this.previsioni.getList(), "JSONdati/" + città + "Previsioni");
	}

	public void load() {
		this.storico.setList((JSONArray) oper.readJSONArray("JSONdati/" + città + "Storico"));
		this.previsioni.setList((JSONArray) oper.readJSONArray("JSONdati/" + città + "Previsioni"));
	}

	public void delatePrevisioni() {
		previsioni.setList(new JSONArray());
	}

	public void delateStorico() {
		storico.setList(new JSONArray());
		storyMap = new HashMap<>();
	}

	public void parseCurrent() {
		JSONObject obj = API.callCurrent();
		JSONObject newObj = new JSONObject();

		JSONObject main = (JSONObject) obj.get("main");

		Long humid = (Long) main.get("humidity");
		Long dt = (Long) obj.get("dt");
		this.storyMap.put(dt, humid);
		newObj.put("Umidità", humid);
		newObj.put("Data", conv.unixToDate(dt));
		this.storico.addFirst(newObj);

	}

	public void parseHistory() {
		JSONObject obj;
		JSONObject newObj = new JSONObject();
		for (int i = 4; i >= 0; i--) {
			obj = API.callHistory(i);
			JSONArray giorno = (JSONArray) obj.get("hourly");
			for (int j = 0; j < giorno.size(); j++) {
				JSONObject ora = (JSONObject) giorno.get(j);
				Long dt = (Long) ora.get("dt");
				Long humid = (Long) ora.get("humidity");

				newObj.put("data", conv.unixToDate(dt));
				newObj.put("umidità", humid);
				this.storyMap.put(dt, humid);
				this.storico.addFirst(newObj);
				newObj = new JSONObject();
			}
		}
	}

	public void parseForecast() {
		JSONObject obj = API.callForecast();
		JSONObject newObj = new JSONObject();

		JSONArray previsione = (JSONArray) obj.get("list");

		for (int i = 0; i < previsione.size(); i++) {
			newObj = new JSONObject();
			JSONObject ora = (JSONObject) previsione.get(i);
			String dt = (String) ora.get("dt_txt");
			JSONObject main = (JSONObject) ora.get("main");
			Long humid = (Long) main.get("humidity");

			newObj.put("data", dt);
			newObj.put("umidità", humid);

			this.previsioni.add(newObj);

		}
	}

	public JSONObject getStat(JSONObject obj) {
		Long inizio;
		Long fine;
		Statistiche stat;
		ArrayList<Long> sample = new ArrayList<>();
		try {
			String init = (String) obj.get("inizio");
			String fin = (String) obj.get("fine");
			inizio = conv.timeToUnix(init);
			fine = conv.timeToUnix(fin);
			
			if(fine < inizio) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "date invertite");
			}

			Iterator<Entry<Long, Long>> iterator = this.storyMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Long, Long> ingresso = iterator.next();

				if ((ingresso.getKey() >= inizio) && (ingresso.getKey() <= fine)) {
					sample.add(ingresso.getValue());
				}
			}
			stat = new Statistiche(sample);

		} catch (ResponseStatusException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "body non corretto");
		}

		return stat.getResolt();

	}
}
