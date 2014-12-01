package com.internal.stocks.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internal.stocks.model.Time;

@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
public class TimeService {

    @GET
    public Response get() {
       return Response.ok(new Time()).build();
    }

}

