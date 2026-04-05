package org.altokeapp.catalog.infrastructure.adapters.rest.adapter;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.altokeapp.catalog.application.port.in.CatalogInputPort;
import org.altokeapp.catalog.infrastructure.adapters.rest.mapper.CatalogRestMapper;
import org.altokeapp.catalog.infrastructure.rest.data.CatalogRequest;

@Path("/api/catalog")
@ApplicationScoped
@RequiredArgsConstructor
public class CatalogCategoryAdapter {

    private final CatalogInputPort catalogInputPort;
    private final CatalogRestMapper mapper;

    @POST
    @WithTransaction
    public Uni<Response> createCategory(@Valid CatalogRequest request) {
        return catalogInputPort.createCategory(mapper.toDomain(request))
                .map(mapper::toResponse)
                .map(response -> Response
                        .status(201)
                        .entity(response)
                        .build());
    }

    @GET
    @WithSession
    public Uni<Response> getAllCategories() {
        return catalogInputPort.getAllCategories()
                .map(list -> Response.ok(list).build());
    }

    @GET
    @Path("/{id}")
    @WithSession
    public Uni<Response> getCategoryById(@PathParam("id") String id) {
        return catalogInputPort.getCategoryById(id)
                .map(mapper::toResponse)
                .map(response -> Response
                        .ok(response)
                        .build());
    }

    @PUT
    @Path("/{id}")
    @WithTransaction
    public Uni<Response> updateCategory(@PathParam("id") String id, @Valid CatalogRequest request) {
        return catalogInputPort.updateCategory(id, mapper.toDomain(request))
                .map(mapper::toResponse)
                .map(response -> Response
                        .ok(response)
                        .build());
    }

    @DELETE
    @Path("/{id}")
    @WithTransaction
    public Uni<Response> deleteCategory(@PathParam("id") String id) {
        return catalogInputPort.deleteCategory(id)
                .map(v -> Response
                        .noContent()
                        .build());
    }
}
