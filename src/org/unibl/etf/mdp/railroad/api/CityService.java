package org.unibl.etf.mdp.railroad.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.unibl.etf.mdp.railroad.data.DataSource;
import org.unibl.etf.mdp.railroad.model.City;

import com.google.gson.Gson;

public class CityService {
	private String key = "cities";
	
	private Gson gson = new Gson();
	
	public ArrayList<City> getCities() {
		List<String> data = DataSource.getValues(key);
		ArrayList<City> cities = new ArrayList<>();
		data.forEach((city) -> {
			cities.add(gson.fromJson(city, City.class));
		});
		
		return cities;
	}
	
	public City getById(String id) {
		JSONObject data = DataSource.getFromMap(key, id);
		return data != null ? gson.fromJson(data.toString(), City.class) : null;
	}
	
	public boolean add(City city) {
		city.setId();
		return DataSource.addToMap(key, city.getId(), new JSONObject(city));
	}
	
	public boolean update(City city) {
		return DataSource.addToMap(key, city.getId(), new JSONObject(city));
	}
	
	public boolean remove(String cityId) {
		return DataSource.removeFromMap(key, cityId);
	}
	
	
}