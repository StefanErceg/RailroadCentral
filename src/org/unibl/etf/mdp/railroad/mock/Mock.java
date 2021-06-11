package org.unibl.etf.mdp.railroad.mock;


import org.unibl.etf.mdp.railroad.api.CityService;
import org.unibl.etf.mdp.railroad.api.CountryService;
import org.unibl.etf.mdp.railroad.api.TrainStationService;
import org.unibl.etf.mdp.railroad.model.City;
import org.unibl.etf.mdp.railroad.model.Country;
import org.unibl.etf.mdp.railroad.model.TrainStation;

public class Mock {
	private static boolean generated = false;

	public static void generateData() {
		if (!generated) {
			CountryService countryService = new CountryService();
			CityService cityService = new CityService();
			TrainStationService trainStationService = new TrainStationService();
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
		
		TrainStation BL = new TrainStation("Glavna zeljeznicka stanica", banjaluka);
		TrainStation BG = new TrainStation("Centralna zeleznicka stanica", belgrade);
		TrainStation MS = new TrainStation("Moscow Leningradsky railway station", moscow);
		trainStationService.add(BL);
		trainStationService.add(BG);
		trainStationService.add(MS);
		}
		
	}
	
	public static void main(String[] args) {
		generateData();
	}
}
