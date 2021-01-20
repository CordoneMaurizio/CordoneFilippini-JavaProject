package wvAPI;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;
//import org.json.simple.JSONObject;

@Component
public class CityList {
	
	public JSONArray cityList;
	//private static int id = 01;
	public CityList() {

	}
	
	@SuppressWarnings("unchecked")
	public String add(String cityName) {
		if(cityList.contains(cityName)) {
			return "Città già presente";
		} else { 
			this.cityList.add(cityName);
			APIOpenWeather api = new APIOpenWeather(cityName);
			getCityList();	
			return "Città inserita";
			}
	}
	
	public String remove(String cityName) {
		if(cityList.contains(cityName)) {
			this.cityList.remove(cityName);
			getCityList();
			return "Città rimossa";	
		} else { 
			return "Città non monitorata";
		}
	}
	
	public JSONArray getCityList() {
		return cityList;
	}
	
	
}
	
