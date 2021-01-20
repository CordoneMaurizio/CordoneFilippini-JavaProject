package wvUtilità;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;

/*import java.util.Map;
 * import com.google.gson.*;
import com.google.gson.reflect.*;
 * vecchio metodo che utilizzava le mappe
 * Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>(){}.getType());
 */


/*@author CordoneMaurizio
 * 
 * La classe contiene i metodi per 
 */
@Component
public class Mapper {

		public Mapper() {}

		public JSONObject objParser(JSONObject obj, String...str){
			for(String s : str) {
				obj = (JSONObject)obj.get(s);	
			}
		return obj;
		}
		
		public JSONArray arrayParser(JSONArray obj, Integer...str){
			for(Integer s : str) {
				obj = (JSONArray)obj.get(s);	
			}
		return obj;
		}
		
}
