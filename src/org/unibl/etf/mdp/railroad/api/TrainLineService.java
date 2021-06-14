package org.unibl.etf.mdp.railroad.api;

import java.util.ArrayList;
import java.util.Date;
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
	
	public ArrayList<TrainLine> getByTrainStation(String trainStationId) {
		List<String> data = DataSource.getValues(key);
		ArrayList<TrainLine> trainLines = new ArrayList<>();
		data.forEach((value) -> {
			TrainLine trainLine = gson.fromJson(value, TrainLine.class);
			if (passesThroughTrainStation(trainLine, trainStationId)) {
				trainLines.add(trainLine);
			}
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
	
	public TrainLine markPassed(String trainLineId, String trainStationId) {
		JSONObject data = DataSource.getFromMap(key, trainLineId);
		if (data != null) {
			TrainLine trainLine = gson.fromJson(data.toString(), TrainLine.class);
			ArrayList<TrainStop> trainStops = trainLine.getStops();
			Integer index = getIndex(trainStops, trainStationId);
			if (index != -1) {
				TrainStop trainStop = trainLine.getStops().get(index);
				trainStop.setActualTime(new Date());
				trainStop.setPassed(true);
				trainLine.getStops().set(index, trainStop);
			}
			if (DataSource.addToMap(key, trainLineId, new JSONObject(trainLine))) {
				return trainLine;
			}
		}
		
		return null;
	}
	
	public boolean remove (String trainLineId) {
		return DataSource.removeFromMap(key, trainLineId);
	}
	
	private Integer getIndex(ArrayList<TrainStop> trainStops, String trainStationId) {
		for (int index = 0; index < trainStops.size(); index++) {
			if (trainStops.get(index).getTrainStation().getId() == trainStationId) return index;  
		}
		return -1;
	}
	
	private boolean passesThroughTrainStation(TrainLine trainLine, String trainStationId) {
		return trainLine.getStart().getId() == trainStationId || trainLine.getDestination().getId() == trainStationId || getIndex(trainLine.getStops(), trainStationId) != -1;
	}
	

}
