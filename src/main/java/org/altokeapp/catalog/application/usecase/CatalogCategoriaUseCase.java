package org.altokeapp.catalog.application.usecase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.altokeapp.catalog.application.dto.CatalogCategoriaRequest;
import org.altokeapp.catalog.application.dto.CatalogCategoriaResponse;
import org.altokeapp.catalog.domain.model.CatalogModel;
import org.altokeapp.catalog.domain.service.CatalogCategoriaService;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CatalogCategoriaUseCase {

    @Inject
    CatalogCategoriaService service;

    public Uni<List<CatalogCategoriaResponse>> getAllCategorias() {
        return service.getAllCategorias()
                .onItem().transform(list ->
                        list.stream()
                                .map(this::toResponse)
                                .collect(Collectors.toList())
                );
    }

    public Uni<CatalogCategoriaResponse> getCategoriaById(String id) {
        return service.getCategoriaById(id)
                .onItem().transform(this::toResponse);
    }

    public Uni<CatalogCategoriaResponse> createCategoria(CatalogCategoriaRequest request) {
        CatalogModel categoria = toDomain(request);
        return service.createCategoria(categoria)
                .onItem().transform(this::toResponse);
    }

    public Uni<CatalogCategoriaResponse> updateCategoria(String id, CatalogCategoriaRequest request) {
        CatalogModel categoria = toDomain(request);
        return service.updateCategoria(id, categoria)
                .onItem().transform(this::toResponse);
    }

    public Uni<Void> deleteCategoria(String id) {
        return service.deleteCategoria(id);
    }

    public Uni<List<CatalogCategoriaResponse>> getCategoriasActivas() {
        return service.getCategoriasActivas()
                .onItem().transform(list ->
                        list.stream()
                                .map(this::toResponse)
                                .collect(Collectors.toList())
                );
    }

    // =======================
    // MAPPERS
    // =======================

    private CatalogModel toDomain(CatalogCategoriaRequest request) {
        return CatalogModel.builder()
                .nombres(request.getNombres())
                .icono(request.getIcono())
                .estado(request.getEstado())
                .build();
    }

    private CatalogCategoriaResponse toResponse(CatalogModel categoria) {
        return new CatalogCategoriaResponse(
                categoria.getId(),
                categoria.getNombres(),
                categoria.getIcono(),
                categoria.getFechaCreacion(),
                categoria.getEstado()
        );
    }
}
