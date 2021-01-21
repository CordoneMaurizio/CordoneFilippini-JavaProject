package wvDefault;

import wvAPI.*;
import wvService.CityList;

import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
    CityList lista = new CityList();

	@GetMapping("/call")
	public JSONObject chiamata() {
		APIOpenWeather API = new APIOpenWeather("termoli");
		return API.callCurrent();	
	}
	
	@GetMapping("/call/{cityName}")
	public JSONObject chiamata(@PathVariable("cityName") String cityName) {
		APIOpenWeather API = new APIOpenWeather(cityName);
		return API.callCurrent();
	}	
	
	@GetMapping("/list")
	public JSONObject getList(){
		return lista.getCityList();
	}

	@GetMapping("/add/{cityName}")
	public void addCity(@PathVariable("cityName") String cityName) {
		lista.add(cityName);
	}
	
	@GetMapping("/remove/{cityName}")
	public void removeCity(@PathVariable("cityName") String cityName) {
		lista.remove(cityName);
	}
	
	@PostMapping("/stats")
	public JSONObject getStat(@RequestBody JSONObject obj) {
		return lista.getStats(obj);
	}
	
	@GetMapping("/update")
	public void update() {
		
	}

}
