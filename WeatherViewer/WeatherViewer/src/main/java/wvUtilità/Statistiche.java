package wvUtilità;

import java.util.ArrayList;

import org.json.simple.JSONObject;

public class Statistiche {
	
	private ArrayList<Long> sample = new ArrayList<>();
	private Long min;
	private Long max;
	private Double media;
	
	public Statistiche(ArrayList<Long> ingresso) {
		this.sample = ingresso;
		this.min = Long.MAX_VALUE;
		this.max = Long.MIN_VALUE;
		calc();	
	}
	
	public JSONObject getResolt() {
		JSONObject obj = new JSONObject();
		obj.put("valore massimo", max);
		obj.put("valore minimo", min);
		obj.put("media", media);
		return obj;
	}
	
	private void calc() {
		int contatore = 0;
		Long accumulatore = 0L;		
		for(Long l : sample) {
			if(l > max)
				max = l;
			if(l < min)
				min = l;
			accumulatore += l;
			contatore++;
		}
		media = (double)accumulatore/contatore;
	}
 
}
