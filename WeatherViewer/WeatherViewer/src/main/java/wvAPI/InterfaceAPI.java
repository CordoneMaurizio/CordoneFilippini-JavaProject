package wvAPI;

import org.json.simple.JSONObject;

public interface InterfaceAPI {

	public JSONObject callCurrent();
	
	public JSONObject callHistory(int i);
	
	public JSONObject callForecast();
	
}
