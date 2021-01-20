package wvAPI;

import org.json.simple.JSONObject;

public interface InterfaceAPI {

	public JSONObject callCurrent();
	
	public JSONObject callHistory(String data);
	
	public JSONObject callForecast();
	
}
