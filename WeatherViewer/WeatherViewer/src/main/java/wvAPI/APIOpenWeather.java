package wvAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import org.springframework.stereotype.Component;

@Component
public class APIOpenWeather implements InterfaceAPI{

	private static String APIkey = "eaab0d56e8e9ef6b813a372c16f220e6";
	
	private String città;
	private Double lat;
	private Double lon;

	public APIOpenWeather(String città) {
		this.città = città;
		setCoord();
	}

	private void setCoord() {
		JSONObject obj = callCurrent();
		JSONObject coord = (JSONObject)obj.get("coord");
		this.lat = (Double) coord.get("lat");
		this.lon = (Double) coord.get("lon");

	}
	
	public void getInfo() {
		System.out.println(
				"Nome città:     " + città + "\n" + "-- latitudine:  " + lat + "\n" + "-- longitudine: " + lon + "\n");

	}

	public JSONObject callCurrent() {
		JSONObject obj = new JSONObject();
		try {
			String urlString ="http://api.openweathermap.org/data/2.5/weather?q=" + this.città + "&appid=" + APIkey + "&units=metric";
			URL url = new URL(urlString);
			URLConnection Connection = url.openConnection();
			
			String testo = "";
			BufferedReader lettore = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
			String line = "";
			while((line = lettore.readLine()) != null) {
				testo += line;
			}
		lettore.close();
			
		obj= (JSONObject)JSONValue.parseWithException(testo);
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		return obj;
		
	}
				
	public JSONObject callHistory(int i) {
			JSONObject obj = new JSONObject();
			
			Long unixDate = System.currentTimeMillis()/1000L;
			
			try {
				String urlString ="https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + this.lat + "&lon=" + this.lon + "&dt=" + (unixDate-((i+1)*86400L) + "&appid=" + APIkey + "&units=metric");
				URL url = new URL(urlString);
				URLConnection Connection  = url.openConnection();
				BufferedReader lettore = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
				
				String testo = "";	
				String line = "";
				while((line = lettore.readLine()) != null) {
					testo += line;
				}
			
				lettore.close();	
				obj = (JSONObject)JSONValue.parseWithException(testo);
				
					
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			return obj;
		}
		
	public JSONObject callForecast() {
		JSONObject obj = new JSONObject();
		
		try {
			String urlString ="http://api.openweathermap.org/data/2.5/forecast?q=" + this.città + "&appid=" + APIkey + "&units=metric";
			URL url = new URL(urlString);
			URLConnection Connection = url.openConnection();
			
			String testo = "";
			BufferedReader lettore = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
			String line = "";
			while((line = lettore.readLine()) != null) {
				testo += line;
			}
			lettore.close();
		
			obj = (JSONObject)JSONValue.parseWithException(testo);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();			
		} 
		return obj;
		 
	}

}