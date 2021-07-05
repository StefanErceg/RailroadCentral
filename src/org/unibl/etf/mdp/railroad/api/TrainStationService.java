package org.unibl.etf.mdp.railroad.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.unibl.etf.mdp.railroad.data.DataSource;
import org.unibl.etf.mdp.railroad.model.TrainStation;

import com.google.gson.Gson;

public class TrainStationService {
	
	private String key = "trainStations";
	
	private Gson gson = new Gson();
	
	public ArrayList<TrainStation> getTrainStations() {
		List<String> data = DataSource.getValues(key);
		ArrayList<TrainStation> trainStations = new ArrayList<>();
		data.forEach((trainStation) -> {
			trainStations.add(gson.fromJson(trainStation, TrainStation.class));
		});
		return trainStations;
	}
	
	public TrainStation getById(String id) {
		JSONObject data = DataSource.getFromMap(key, id);
		return data != null ? gson.fromJson(data.toString(), TrainStation.class) : null;
	}
	
	public boolean add(TrainStation trainStation) {
		trainStation.setId();
		return DataSource.addToMap(key, trainStation.getId(), new JSONObject(trainStation));
	}
	
	public boolean update(TrainStation trainStation) {
		return DataSource.addToMap(key, trainStation.getId(), new JSONObject(trainStation));
	}
	
	public boolean remove (String trainStationId) {
		return DataSource.removeFromMap(key, trainStationId);
	}

}
