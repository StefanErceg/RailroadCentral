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

import org.unibl.etf.mdp.railroad.model.TrainLine;

@Path("/trainLines")
public class TrainLineAPI {
	
	TrainLineService service = new TrainLineService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<TrainLine> getAll() {
		return service.getAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") String id) {
		TrainLine trainLine = service.getById(id);
		if (trainLine != null) {
			return Response.status(200).entity(trainLine).build();
		}
		return Response.status(404).build();
	}
	
	@GET
	@Path("/byTrainStation/{trainStationId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<TrainLine> getByTrainStation(@PathParam("trainStationId") String trainStationId) {
		return service.getByTrainStation(trainStationId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add (TrainLine trainLine) {
		if (service.add(trainLine)) {
			return Response.status(200).entity(trainLine).build();
		}
		return Response.status(500).entity("Problem with adding train line").build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(TrainLine trainLine, @PathParam("id") String id) {
		if (service.update(trainLine)) {
			return Response.status(200).entity(trainLine).build();
		}
		return Response.status(500).build();
	}
	
	@PUT
	@Path("/{id}/{trainStationId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response markPassed(@PathParam("id") String id, @PathParam("trainStationId") String trainStationId) {
		TrainLine updated = service.markPassed(id, trainStationId);
		if (updated != null) {
			return Response.status(200).entity(updated).build();
		}
		return Response.status(500).build();
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
