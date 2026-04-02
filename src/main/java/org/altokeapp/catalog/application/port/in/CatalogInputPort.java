package org.altokeapp.catalog.application.port.in;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.altokeapp.catalog.domain.model.CatalogModel;

public interface CatalogInputPort {
    Uni<CatalogModel> createCategoria(CatalogModel catalogModel);
    Uni<CatalogModel> getCategoriaById(String id);
    Multi<CatalogModel> getAllCategorias();
    Uni<CatalogModel> updateCategoria(String id, CatalogModel catalogModel);
    Uni<Void> deleteCategoria(String id);
}
