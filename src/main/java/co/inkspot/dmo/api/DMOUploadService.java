/*
 * Copyright (C) 2022 Inkspot Science.
 * All rights reserved.
 */
package co.inkspot.dmo.api;

import co.inkspot.dmo.model.Collection;
import co.inkspot.osm.services.api.utils.OSMAlternatePath;
import co.inkspot.osm.services.api.utils.OSMRootPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author hugo
 */
@OSMRootPath("/dmos")
@OSMAlternatePath("/dmos/upload")
public interface DMOUploadService {
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test();

    
    @POST
    @Path("/collections")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String createCollectionWithMetadata(String metadata) throws Exception;    
    
    @POST
    @Path("/collections/get")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection getCollection(@FormParam("id")String id) throws Exception;
}
