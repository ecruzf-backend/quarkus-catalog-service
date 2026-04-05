package org.altokeapp.catalog.application.port.in;

import io.smallrye.mutiny.Uni;
import org.altokeapp.catalog.domain.model.CatalogModel;

import java.util.List;

public interface CatalogInputPort {
    Uni<CatalogModel> createCategory(CatalogModel catalogModel);
    Uni<CatalogModel> getCategoryById(String id);
    Uni<List<CatalogModel>> getAllCategories();
    Uni<CatalogModel> updateCategory(String id, CatalogModel catalogModel);
    Uni<Void> deleteCategory(String id);
}
