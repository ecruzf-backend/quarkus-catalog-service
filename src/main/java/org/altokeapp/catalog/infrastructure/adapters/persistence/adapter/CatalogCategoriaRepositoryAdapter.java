package org.altokeapp.catalog.infrastructure.adapters.persistence.adapter;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.altokeapp.catalog.application.port.out.CatalogOutputPort;
import org.altokeapp.catalog.domain.model.CatalogModel;
import org.altokeapp.catalog.infrastructure.adapters.persistence.repository.CatalogCategoriaPanacheRepository;

import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class CatalogCategoriaRepositoryAdapter implements CatalogOutputPort {

    private final CatalogCategoriaPanacheRepository catalogCategoriaRepository;
    @Override
    public Uni<CatalogModel> createCategoria(CatalogModel catalogModel) {
        return null;
    }

    @Override
    public Uni<Optional<CatalogModel>> getCategoriaById(String id) {
        return null;
    }

    @Override
    public Multi<CatalogModel> getAllCategorias() {
        return null;
    }

    @Override
    public Uni<CatalogModel> updateCategoria(String id, CatalogModel catalogModel) {
        return null;
    }

    @Override
    public Uni<Void> deleteCategoria(String id) {
        return null;
    }

    @Override
    public Uni<Boolean> existsByNombre(String nombre) {
        return null;
    }

    @Override
    public Uni<Optional<CatalogModel>> findByNombre(String nombre) {
        return null;
    }
}
