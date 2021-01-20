package wvAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;

import wvJSON.*;
import wvUtilità.UnixConverter;

@Component
public class APIOpenWeather implements InterfaceAPI{

	static String APIkey = "eaab0d56e8e9ef6b813a372c16f220e6";

	public JSONObject objCurrent;
	public JSONObject objForecast;
	public JSONObject objHistory;

	private String città;
	private Double lat;
	private Double lon;
	private Boolean setted = false;

	JSONform form = new JSONform();
	

	public APIOpenWeather(String città) {
		this.città = città;
	}

	private void setCoord(JSONObject obj) {

		JSONObject coord = (JSONObject)obj.get("coord");

		this.lat = (Double) coord.get("lat");
		this.lon = (Double) coord.get("lon");

	}

	public void getInfo() {
		System.out.println(
				"Nome città:     " + città + "\n" + "-- latitudine:  " + lat + "\n" + "-- longitudine: " + lon + "\n");

	}

	public JSONObject callCurrent() {
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
			
		JSONObject obj= (JSONObject)JSONValue.parseWithException(testo);
			
		if(!setted) {
			setCoord(obj);
		 }
			 
		//objCurrent = obj;
		objCurrent = form.build(obj);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		return objCurrent;
		
		}
		
		
		public JSONObject callHistory(String data) {
			Long unixDate = 0L;
			unixDate = UnixConverter.timeToUnix(data);
			
			try {
				
				String urlString ="https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + this.lat + "&lon=" + this.lon + "&dt=" + unixDate + "&appid=" + APIkey + "&units=metric";
				URL url = new URL(urlString);
				URLConnection Connection  = url.openConnection();
				BufferedReader lettore = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
				
				String testo = "";	
				String line = "";
				while((line = lettore.readLine()) != null) {
					testo += line;
				}
				
				lettore.close();
							
		        objHistory = (JSONObject)JSONValue.parseWithException(testo);		
					
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
				
		return objHistory;
			
		}
		
		public JSONObject callForecast() {
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
			
				objForecast = (JSONObject)JSONValue.parseWithException(testo);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			
			return objForecast;
		}

}