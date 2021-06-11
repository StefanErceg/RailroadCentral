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

import org.unibl.etf.mdp.railroad.model.TrainStation;

@Path("/trainStations")
public class TrainStationAPI {
	
	TrainStationService service = new TrainStationService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<TrainStation> getAll() {
		return service.getTrainStations();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") String id) {
		TrainStation trainStation = service.getById(id);
		if (trainStation != null) {
			return Response.status(200).entity(trainStation).build();
		}
		return Response.status(404).build();
	} 
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(TrainStation trainStation) {
		if (service.add(trainStation)) {
			Response.status(200).entity(trainStation).build();
		}
		return Response.status(500).entity("Problem with adding train station").build();
	}
	
	@PUT
	@Path("/id")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(TrainStation trainStation, @PathParam("id") String id) {
		if (service.update(trainStation)) {
			return Response.status(200).entity(trainStation).build();
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
