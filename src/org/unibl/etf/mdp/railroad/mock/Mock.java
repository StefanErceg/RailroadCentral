package org.unibl.etf.mdp.railroad.mock;


import org.unibl.etf.mdp.railroad.api.CityService;
import org.unibl.etf.mdp.railroad.api.CountryService;
import org.unibl.etf.mdp.railroad.model.City;
import org.unibl.etf.mdp.railroad.model.Country;

public class Mock {
	private static boolean generated = false;

	public static void generateData() {
		if (!generated) {
			CountryService countryService = new CountryService();
			CityService cityService = new CityService();
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
		}
		
	}
	
	public static void main(String[] args) {
		generateData();
	}
}
