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
import org.springframework.web.server.ResponseStatusException;

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
	public JSONObject addCity(@PathVariable("cityName") String cityName) {
		lista.add(cityName);
		return lista.getCityList();
	}
	
	@GetMapping("/remove/{cityName}")
	public JSONObject removeCity(@PathVariable("cityName") String cityName) {
		lista.remove(cityName);
		return lista.getCityList();
	}
	
	@PostMapping("/stats")
	public JSONObject getStat(@RequestBody JSONObject obj) {
		return lista.getStats(obj);
	}
	
	@GetMapping("/update")
	public void update() {
		lista.update();
	}

}
