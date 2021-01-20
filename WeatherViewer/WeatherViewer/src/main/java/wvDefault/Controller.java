package wvDefault;

import wvAPI.*;
import wvUtilità.Mapper;

import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	Mapper map = new Mapper();
    CityList lista = new CityList();

	
	
	@GetMapping("/call")
	public JSONObject chiamata() {
		APIOpenWeather API = new APIOpenWeather("termoli");
		return API.callCurrent();	
	}
	
	@GetMapping("/call/{cityName}")
	public JSONObject chiamata(@PathVariable("cityMame") String cityName) {
		APIOpenWeather API = new APIOpenWeather(cityName);;
		return API.callCurrent();

	}

	@GetMapping("/add/{cityName}")
	public void addCity(@PathVariable("cityName") String cityName) {
		lista.add(cityName);
	}
	
	@GetMapping("/remove/{cityName}")
	public void removeCity(@PathVariable("cityName") String cityName) {
		lista.remove(cityName);
	}
	 
	
	/*
	
	@PostMapping("/history/")
	public JSONObject cityHistory(@RequestBody JSONObject request) throws JSONException {
		String start = (String) request.get("startDate");
		String end = (String) request.get("endDate");
		return request;
	}
	
	@GetMapping("/city/{cityName}")
	public JSONObject testParameters(@PathVariable("cityName") String cityName) {
		APIOpenWeather API = new APIOpenWeather(cityName);
		return API.getFinalJson();
	}

	@GetMapping("/city/{cityName}/{startDate}/{endDate}")
	public JSONObject testParameters(@PathVariable("cityName") String cityName,
			@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
		
		APIOpenWeather API = new APIOpenWeather(cityName);
		return API.getFinalJson();
	}
	
	*/

}
