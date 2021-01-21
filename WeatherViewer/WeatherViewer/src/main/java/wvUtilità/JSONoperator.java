package wvUtilità;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


/*@uthor CordoneMaurizio
 * 
 * La classe si occupa della lettura e salvataggio di oggetti in formato JSON
 * all' interno di appositi file.JSON utilizzati come database.
 */

@Component
public class JSONoperator {
	
	public void writeJSON(JSONObject obj, String file) {

        try {
            BufferedWriter jsonFileWriter = new BufferedWriter(new FileWriter(file));
            jsonFileWriter.write(obj.toJSONString());
            jsonFileWriter.flush();
            jsonFileWriter.close();

        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
	
	public void writeJSONArrey(JSONArray obj, String file) {

        try {
            BufferedWriter jsonFileWriter = new BufferedWriter(new FileWriter(file));
            jsonFileWriter.write(obj.toJSONString());
            jsonFileWriter.flush();
            jsonFileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	public JSONObject readJSON(String file) {
        JSONParser parser = new JSONParser();
        JSONObject obj = null;

        try {
            BufferedReader jsonFileReader = new BufferedReader(new FileReader(file));
            obj = (JSONObject) parser.parse(jsonFileReader);
            
        } catch (IOException e){
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return obj;
    }
	
	public JSONArray readJSONArray(String file) {
        JSONParser parser = new JSONParser();
        JSONArray obj = null;

        try {
            BufferedReader jsonFileReader = new BufferedReader(new FileReader(file));
            obj = (JSONArray) parser.parse(jsonFileReader);
            
        } catch (IOException e){
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return obj;
    }

}
