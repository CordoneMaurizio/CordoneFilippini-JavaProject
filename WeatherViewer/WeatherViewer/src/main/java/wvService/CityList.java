package wvService;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import wvList.DataList;
import wvUtilità.JSONoperator;

@SuppressWarnings("unchecked")

@Service
public class CityList extends DataList {

	JSONoperator oper = new JSONoperator();
	ArrayList<JSONform> formList = new ArrayList<>();

	public CityList() {
		super();
	}

	public void save() {
		oper.writeJSONArrey(this.list, "JSONdati/listacittà");
	}

	public void load() {
		oper.readJSONArray("JSONdati/listacittà");
	}

	public void add(String city) {
		if (this.list.contains(city)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "città già monitorata");
		} else {
			
			JSONform form = new JSONform(city);
			formList.add(form);
			form.parseHistory();
			form.parseForecast();
			form.save();
			this.list.add(city);
			this.save();
		}
	}

	public void remove(String city) {
		if (this.list.contains(city)) {
			int i = list.indexOf(city);
			formList.get(i).delatePrevisioni();
			formList.get(i).delateStorico();
			formList.get(i).save();
			formList.remove(i);
			this.list.remove(city);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "città non presente");
		}
	}

	public JSONObject getCityList() {
		JSONObject obj = new JSONObject();
		obj.put("città monitorate", this.list);
		return obj;
	}

	public JSONObject getStats(JSONObject obj) {
		JSONObject newObj = new JSONObject();
		for (JSONform form : formList) {
			JSONObject objTemp = new JSONObject();
			objTemp.put("dati storici", form.getStorico());
			objTemp.put("Statistiche su arco temporale scelto", form.getStat(obj));
			objTemp.put("previsioni", form.getPrevisioni());
			newObj.put(form.getNome(), objTemp);
		}
		return newObj;
	}

	public void update() {
		for (JSONform form : formList) {
			form.parseCurrent();
			form.delatePrevisioni();
			form.parseForecast();
		}
	}

}
