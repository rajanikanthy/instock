package com.internal.stocks.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.internal.stocks.batch.executor.BatchJobExecutor;

@Path("/symbols")
public class SymbolImportService {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(SymbolImportService.class);
	
	@Autowired
	private BatchJobExecutor batchJobExecutor;
	
	private static final String SYMBOL_FILE_IMPORT_JOB = "stocks-import";
	
	@POST
	@Path("/import")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadSymbols(MultipartBody multipartBody){
		Response response = Response.ok().build();
		try {
				java.nio.file.Path symbolFile = Files.createTempFile("symboldata", "" + Calendar.getInstance().getTimeInMillis() + ".txt");
				Files.copy( multipartBody.getAllAttachments().get(0).getDataHandler().getInputStream(), symbolFile, StandardCopyOption.REPLACE_EXISTING);
				String parameters  = StringUtils.join(new String[] { "symbolFile=" + StringEscapeUtils.escapeJava(symbolFile.toString()) } , "," );
				batchJobExecutor.runJob(SYMBOL_FILE_IMPORT_JOB, parameters);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			response = Response.status(Status.BAD_REQUEST).entity("Failed to import file ").build();
		}
		return response;
	}
}	
