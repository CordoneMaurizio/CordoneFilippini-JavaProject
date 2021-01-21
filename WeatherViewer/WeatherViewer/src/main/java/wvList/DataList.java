package wvList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataList {
	
	protected JSONArray list;
	
	public DataList() {
		this.list = new JSONArray();
	}
	
	public void add(JSONObject obj) {
		this.list.add(obj);
	}
	public void addFirst(JSONObject obj) {
		this.list.add(0, obj);
	}
	public void remove(int i) {
		this.list.remove(i);
	}
	
	public JSONArray getList() {
		return this.list;
	}
	
	public void setList(JSONArray obj) {
		this.list = obj;
	}

}
