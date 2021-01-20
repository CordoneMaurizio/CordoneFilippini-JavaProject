package wvJSON;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;


/*@uthor CordoneMaurizio
 * 
 * La classe si occupa della lettura e salvataggio di oggetti in formato JSON
 * all' interno di appositi file.JSON utilizzati come database.
 */

@Component
public class JSONoperator {
	
private JSONObject obj;
	
	public JSONoperator(String str) {
		
		obj = (JSONObject)JSONValue.parse(str);
	}
	
	public JSONoperator() {
	}
	
	public void writeJson(String file) {

        try {
        	
        	System.out.println("Scrittura JSON nel file: " + file);
            BufferedWriter jsonFileWriter = new BufferedWriter(new FileWriter(file, true));
            jsonFileWriter.write(obj.toJSONString());
            jsonFileWriter.flush();
            jsonFileWriter.close();
            System.out.println("... scrittura completata");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void readJson(String file) {
        JSONParser parser = new JSONParser();

        try {
            System.out.println("Reading JSON file from Java program");
            BufferedReader jsonFileReader = new BufferedReader(new FileReader(file));
            obj = (JSONObject) parser.parse(jsonFileReader);
            
        } catch (IOException e){
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

}
