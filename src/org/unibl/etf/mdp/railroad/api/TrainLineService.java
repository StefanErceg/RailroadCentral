package org.unibl.etf.mdp.railroad.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.unibl.etf.mdp.railroad.data.DataSource;
import org.unibl.etf.mdp.railroad.model.TrainLine;
import org.unibl.etf.mdp.railroad.model.TrainStop;

import com.google.gson.Gson;

public class TrainLineService {
	
	private String key = "trainLines";
	
	private Gson gson = new Gson();
	
	public ArrayList<TrainLine> getAll() {
		List<String> data = DataSource.getValues(key);
		ArrayList<TrainLine> trainLines = new ArrayList<>();
		data.forEach((trainLine) -> {
			trainLines.add(gson.fromJson(trainLine, TrainLine.class));
		});
		return trainLines;
	}
	
	public TrainLine getById(String id) {
		JSONObject data = DataSource.getFromMap(key, id);
		return data != null ?  gson.fromJson(data.toString(), TrainLine.class) : null;
	}
	
	public TrainLine getByTrainStop(String trainStopId) {
		ArrayList<TrainLine> allLines = getAll();
		return null;
	}
	
	public boolean add (TrainLine trainLine) {
		return DataSource.addToMap(key, trainLine.getId(), new JSONObject(trainLine));
	}
	
	public boolean update (TrainLine trainLine) {
		return DataSource.addToMap(key, trainLine.getId(), new JSONObject(trainLine));
	}
	
	public boolean markPassed(String trainLineId, TrainStop trainStop) {
		JSONObject data = DataSource.getFromMap(key, trainLineId);
		if (data != null) {
			TrainLine trainLine = gson.fromJson(data.toString(), TrainLine.class);
			Integer index = trainLine.getStops().indexOf(trainStop); // equals is overridden so search will be by id only
			if (index != -1) {
				trainLine.getStops().set(index, trainStop);
			}
			return DataSource.addToMap(key, trainLineId, new JSONObject(trainLine));
		}
		
		return false;
	}
	
	public boolean remove (String trainLineId) {
		return DataSource.removeFromMap(key, trainLineId);
	}
	

}
