package org.unibl.etf.mdp.railroad.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.unibl.etf.mdp.railroad.data.DataSource;
import org.unibl.etf.mdp.railroad.model.Country;

import com.google.gson.Gson;


import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


public class CountryService {
	
	private String key = "countries";
	
	private Gson gson = new Gson();
	
	public ArrayList<Country> getCountries() {
		List<String> data = (DataSource.getValues(key));
		ArrayList<Country> countries = new ArrayList<>();
		data.forEach((country) -> {
			countries.add(gson.fromJson(country, Country.class));
		});
		return countries;
	}
	
	public Country getById(String id) {
		JSONObject data = DataSource.getFromMap(key, id);
		return data.toString() != null ? gson.fromJson(data.toString(), Country.class) : null;
	}
	
	public boolean add(Country country) {
		return DataSource.addToMap(key, country.getId(), new JSONObject(country));
	};
	
	public boolean update(Country country) {
		return DataSource.addToMap(key, country.getId(), new JSONObject(country));
	}
	
	public boolean remove(String countryId) {
		return DataSource.removeFromMap(key, countryId);
	}
	
	
}
