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
	private Map<Long,Long> storyMap = new HashMap<Long,Long>();
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
		oper.writeJSONArrey(this.storico.getList(), "JSONdati/"+città+"Storico");
    	oper.writeJSONArrey(this.previsioni.getList(), "JSONdati/"+città+"Previsioni");
	}
	
	public void load() {
	    	this.storico.setList((JSONArray)oper.readJSONArray("JSONdati/"+città+"Storico")); 
	    	this.previsioni.setList((JSONArray)oper.readJSONArray("JSONdati/"+città+"Previsioni"));
	}
	
	public void delate() {
		previsioni.setList(null);
	}
	
	public void parseCurrent() {
		JSONObject obj = API.callCurrent();
		JSONObject newObj = new JSONObject();
		
		JSONObject main = (JSONObject)obj.get("main");
		
	    Long humid = (Long)main.get("humidity");
		Long dt = (Long)obj.get("dt");
		this.storyMap.put(dt, humid);
		newObj.put("Umidità", humid);
		newObj.put("Data", conv.unixToDate(dt));
		this.storico.addFirst(newObj);

	}
	
	public void parseHistory() {
		JSONObject obj;
		JSONObject newObj = new JSONObject();
		for(int i = 0; i < 5; i++) {
			obj = API.callHistory(i);
			JSONObject current = (JSONObject)obj.get("current");
			Long dt = (Long)current.get("dt");
			Long humid = (Long)current.get("humidity");
		
			newObj.put("data",conv.unixToDate(dt));
			newObj.put("umidità", humid);
			this.storico.addFirst(newObj);
			this.storyMap.put(dt, humid);
			newObj = null;
			
			JSONArray giorno = (JSONArray)obj.get("hourly");
			for(int j = 0; j < giorno.size() ; j--) {
				JSONObject ora = (JSONObject)giorno.get(j);
				dt = (Long)ora.get("dt");
				humid = (Long)ora.get("humidity");
		
				newObj.put("data",conv.unixToDate(dt));
				newObj.put("umidità", humid);
				this.storyMap.put(dt, humid);
				this.storico.addFirst(newObj);
				newObj = null;
			}			
		}		
	}
	
	public void parseForecast() {
		JSONObject obj = API.callForecast();
		JSONObject newObj = new JSONObject();
		
		JSONArray previsione = (JSONArray)obj.get("hourly");
		for(int i = 0; i< previsione.size(); i++ ) {
			JSONObject ora = (JSONObject)previsione.get(i);
			Long dt = (Long)ora.get("dt");
			JSONObject main = (JSONObject)ora.get("main");
			Long humid = (Long)main.get("humidity");
			
			newObj.put("data",conv.unixToDate(dt));
			newObj.put("umidità", humid);
			
			this.previsioni.add(newObj);
			
		}	
	}

	public JSONObject getStat(JSONObject obj) {
		Long inizio;
		Long fine;		
		try {
			String init = (String)obj.get("inizio");
			String fin = (String)obj.get("fine");
			inizio = conv.timeToUnix(init);
			fine = conv.timeToUnix(fin);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"body non corretto");
		}		
		
		ArrayList<Long> sample = new ArrayList<>();
		
		Iterator<Entry<Long,Long>> iterator = this.storyMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long,Long> ingresso = iterator.next();
			if((ingresso.getKey() >= inizio) && (ingresso.getKey() <= fine)) {
				sample.add(ingresso.getValue());
			}	
		}
		Statistiche stat = new Statistiche(sample);
		return stat.getResolt();
		
	}
}
