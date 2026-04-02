package org.altokeapp.catalog.application.port.out;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.altokeapp.catalog.domain.model.CatalogModel;

import java.util.Optional;

public interface CatalogOutputPort {
    Uni<CatalogModel> createCategoria(CatalogModel catalogModel);
    Uni<Optional<CatalogModel>> getCategoriaById(String id);
    Multi<CatalogModel> getAllCategorias();
    Uni<CatalogModel> updateCategoria(String id, CatalogModel catalogModel);
    Uni<Void> deleteCategoria(String id);
    Uni<Boolean> existsByNombre(String nombre);
    Uni<Optional<CatalogModel>> findByNombre(String nombre);
}
