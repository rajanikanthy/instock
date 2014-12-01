package com.internal.stocks.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/symbols")
public class SymbolImportService {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(SymbolImportService.class);
	
	@POST
	@Path("/import")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadSymbols(){
		return Response.ok().build();
	}
}	
