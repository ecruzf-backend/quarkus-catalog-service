package org.altokeapp.catalog.domain.repository;

import io.smallrye.mutiny.Uni;
import org.altokeapp.catalog.domain.model.CatalogModel;

import java.util.List;
import java.util.Optional;

public interface CatalogCategoriaRepository {
    Uni<List<CatalogModel>> findAll();
    Uni<Optional<CatalogModel>> findById(String id);
    Uni<CatalogModel> save(CatalogModel catalogModel);
    Uni<CatalogModel> update(CatalogModel catalogModel);
    Uni<Boolean> deleteById(String id);
    Uni<List<CatalogModel>> findByEstado(Integer estado);
}