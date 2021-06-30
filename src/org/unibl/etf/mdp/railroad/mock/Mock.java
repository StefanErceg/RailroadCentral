package org.unibl.etf.mdp.railroad.mock;


import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.mdp.railroad.api.CityService;
import org.unibl.etf.mdp.railroad.api.CountryService;
import org.unibl.etf.mdp.railroad.api.TrainLineService;
import org.unibl.etf.mdp.railroad.api.TrainStationService;
import org.unibl.etf.mdp.railroad.model.City;
import org.unibl.etf.mdp.railroad.model.Country;
import org.unibl.etf.mdp.railroad.model.TrainLine;
import org.unibl.etf.mdp.railroad.model.TrainStation;
import org.unibl.etf.mdp.railroad.model.TrainStop;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Mock {

	public static void generateData() {
		JedisPool pool = new JedisPool("localhost");
		try (Jedis jedis = pool.getResource()) {
		jedis.del("countries");
		jedis.del("cities");
		jedis.del("trainStations");
		jedis.del("trainLines");

		CountryService countryService = new CountryService();
		CityService cityService = new CityService();
		TrainStationService trainStationService = new TrainStationService();
		TrainLineService trainLineService = new TrainLineService();
		
		Country serbia = new Country("Serbia", "SR");
		Country republicOfSrpska = new  Country("Republic of Srpska", "RS");
		Country russia = new Country("Russia", "RU");
		Country france = new Country("France", "FR");
		Country deutchland = new Country("Deutchland", "DE");
		Country greece = new Country("Greece", "GR");
		countryService.add(serbia);
		countryService.add(republicOfSrpska);
		countryService.add(russia);
		countryService.add(france);
		countryService.add(deutchland);
		countryService.add(greece);
		
		City banjaluka = new City("Banja Luka", republicOfSrpska);
		City belgrade = new City("Belgrade", serbia);
		City moscow = new City("Moscow", russia);
		City paris = new City("Paris", france);
		City munich = new City("Munich", deutchland);
		City atina = new City("Atina", greece);
		cityService.add(banjaluka);
		cityService.add(belgrade);
		cityService.add(moscow);
		cityService.add(paris);
		cityService.add(munich);
		cityService.add(atina);
		
		TrainStation BL = new TrainStation("835d16db-fdd0-4093-aab0-3b3ac8fbc1a3","Glavna zeljeznicka stanica", banjaluka);
		TrainStation BG = new TrainStation("cb761faa-1804-4d9a-8f73-03470b40fc2b","Centralna zeleznicka stanica", belgrade);
		TrainStation MS = new TrainStation("173c677b-743d-455b-84a2-31ae97028d0c","Moscow Leningradsky railway station", moscow);
		trainStationService.add(BL);
		trainStationService.add(BG);
		trainStationService.add(MS);
	
		Date today = new Date();
		Date tomorrow = new Date();
		
		ArrayList<TrainStop> trainStops1 = new ArrayList<>();
		trainStops1.add(new TrainStop(BL, today.toString(), false, null));
		trainStops1.add(new TrainStop(BG, tomorrow.toString(), false, null));
		TrainLine trainLine1 = new TrainLine(BL, BG, today.toString(), tomorrow.toString(), trainStops1);
		
		ArrayList<TrainStop> trainStops2 = new ArrayList<>();
		trainStops2.add(new TrainStop(BL, today.toString(), false, null));
		trainStops2.add(new TrainStop(MS, tomorrow.toString(), false, null));
		TrainLine trainLine2 = new TrainLine(BL, MS, today.toString(), tomorrow.toString(), trainStops2);
		
		
		trainLineService.add(trainLine2);
		trainLineService.add(trainLine1);
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.close();
		}
		
	}
	
	public static void main(String[] args) {
		generateData();
	}
}
