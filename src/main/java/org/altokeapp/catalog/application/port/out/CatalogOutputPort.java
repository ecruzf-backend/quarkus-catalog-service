package org.altokeapp.catalog.application.port.out;

import io.smallrye.mutiny.Uni;
import org.altokeapp.catalog.domain.model.CatalogModel;

import java.util.List;
import java.util.Optional;

public interface CatalogOutputPort {
    Uni<CatalogModel> createCategory(CatalogModel catalogModel);
    Uni<Optional<CatalogModel>> getCategoryById(String id);
    Uni<List<CatalogModel>> getAllCategories();
    Uni<CatalogModel> updateCategory(String id, CatalogModel catalogModel);
    Uni<Void> deleteCategory(String id);
    Uni<Boolean> existsByName(String name);
    Uni<Optional<CatalogModel>> findByName(String name);
}
