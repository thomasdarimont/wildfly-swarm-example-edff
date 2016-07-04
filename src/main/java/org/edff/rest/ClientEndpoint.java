package org.edff.rest;

import org.edff.beans.ClientService;
import org.edff.model.Client;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * @author tom
 */
@Path("/client")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientEndpoint {

    @Inject
    ClientService clientService;

    @Context
    UriInfo uriInfo;

    @GET
    public Response getClients() {

        return Response.ok(clientService.getClients()).build();
    }

    /**
     * <pre>curl -v -H "Content-Type: application/json" -d "{\"name\":\"test\"}" -X POST http://localhost:8080/edff/api/client/</pre>
     * @param client
     * @return
     */
    @POST
    public Response createClient(Client client) {

        Client created = clientService.createClient(client);

        URI build = uriInfo.getAbsolutePathBuilder().path("" + created.getId()).build();
        return Response.created(build).build();
    }

    /**
     * <pre>curl -v -H "Content-Type: application/json" http://localhost:8080/edff/api/client/1</pre>
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {

        Client client = clientService.findOneById(id);

        if (client != null) {
            return Response.ok(client).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}