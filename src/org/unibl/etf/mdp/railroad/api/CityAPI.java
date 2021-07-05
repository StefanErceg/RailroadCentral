package org.unibl.etf.mdp.railroad.api;

import java.util.ArrayList;

import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.mdp.railroad.model.City;

import com.google.gson.Gson;

@Path("/cities")
public class CityAPI {
	
	CityService service;
	Gson gson = new Gson();
	
	public CityAPI() {
		service = new CityService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<City> getAll() {
		return service.getCities();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") String id) {
		City city = service.getById(id);
		if (city != null) {
			return Response.status(200).entity(city).build();
		}
		return Response.status(404).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(String body) {
		City city = gson.fromJson(body, City.class);
		if (service.add(city)) {
			return Response.status(200).entity(city).build();
		}
		return Response.status(500).entity("Problem with adding city").build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(City city, @PathParam("id") String id) {
		if (service.update(city)) {
			return Response.status(200).entity(city).build();
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
