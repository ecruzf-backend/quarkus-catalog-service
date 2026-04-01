package org.altokeapp.catalog.infrastructure.rest.controller;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.altokeapp.catalog.application.dto.CatalogCategoriaRequest;
import org.altokeapp.catalog.application.usecase.CatalogCategoriaUseCase;

@Path("/api/v1/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogCategoriaResource {

    @Inject
    CatalogCategoriaUseCase useCase;

    @GET
    public Uni<Response> getAllCategorias() {
        return useCase.getAllCategorias()
                .onItem().transform(Response::ok)
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getCategoriaById(@PathParam("id") String id) {
        return useCase.getCategoriaById(id)
                .onItem().transform(Response::ok)
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @POST
    @WithTransaction
    public Uni<Response> createCategoria(@Valid CatalogCategoriaRequest request) {
        return useCase.createCategoria(request)
                .onItem().transform(created ->
                        Response.status(Response.Status.CREATED)
                                .entity(created)
                                .build()
                );
    }

    @PUT
    @Path("/{id}")
    @WithTransaction
    public Uni<Response> updateCategoria(
            @PathParam("id") String id,
            @Valid CatalogCategoriaRequest request) {

        return useCase.updateCategoria(id, request)
                .onItem().transform(Response::ok)
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @DELETE
    @Path("/{id}")
    @WithTransaction
    public Uni<Response> deleteCategoria(@PathParam("id") String id) {
        return useCase.deleteCategoria(id)
                .onItem().transform(v -> Response.noContent().build());
    }

    @GET
    @Path("/activas")
    public Uni<Response> getCategoriasActivas() {
        return useCase.getCategoriasActivas()
                .onItem().transform(Response::ok)
                .onItem().transform(Response.ResponseBuilder::build);
    }
}
