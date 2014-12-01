package com.internal.stocks.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/symbols")
public class SymbolImportService {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(SymbolImportService.class);
	
	@POST
	@Path("/import")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadSymbols(File file){
		Response response = Response.ok().build();
		try {
			if ( file == null) {
				response = Response.status(Status.BAD_REQUEST).entity("Invalid file").build();
			} else {
				java.nio.file.Path symbolFile = Files.createTempFile("symboldata", "" + Calendar.getInstance().getTimeInMillis() + ".txt");
				InputStream inputStream = new FileInputStream(file);
				Files.copy(inputStream, symbolFile);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			response = Response.status(Status.BAD_REQUEST).entity("Failed to import file " + file).build();
		}
		
		
		return response;
	}
}	
