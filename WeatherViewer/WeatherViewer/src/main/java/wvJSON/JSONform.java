package wvJSON;

import org.json.simple.JSONObject;

public class JSONform {
	
	public JSONform() {}
	
	@SuppressWarnings("unchecked")
	
	public JSONObject build(JSONObject obj) {
		
		JSONObject newObj = new JSONObject();
		
		JSONObject main = (JSONObject)obj.get("main");
		
		String nome = (String) obj.get("name");
	    Long humid = (Long)main.get("humidity");
		Double temp = (Double)main.get("temp");
		
		newObj.put("nome", nome);
		newObj.put("umidità", humid);
		newObj.put("temperatura", temp);
		
		return newObj;

	}

}
