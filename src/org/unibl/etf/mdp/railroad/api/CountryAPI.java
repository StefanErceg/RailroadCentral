package org.unibl.etf.mdp.railroad.api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.mdp.railroad.model.Country;

import com.google.gson.Gson;

@Path("/countries")
public class CountryAPI {
	
	CountryService service;
	Gson gson = new Gson();
	
	public CountryAPI() {
		service = new CountryService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Country> getAll() {
		return service.getCountries();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") String id) {
		Country country = service.getById(id);
		if (country != null) {
			return Response.status(200).entity(country).build();
		}
		return Response.status(404).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(String body) {
		Country country = gson.fromJson(body, Country.class);
		if (service.add(country)) {
			return Response.status(200).entity(country).build();
		}
		return Response.status(500).entity("Problem with adding country").build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Country country, @PathParam("id") String id) {
		if (service.update(country)) {
			return Response.status(200).entity(country).build();
		}
		return Response.status(404).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") String id) {
		if (service.remove(id)) {
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}
	
}
