package org.altokeapp.catalog.domain.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.altokeapp.catalog.domain.exception.ResourceNotFoundException;
import org.altokeapp.catalog.domain.model.CatalogModel;
import org.altokeapp.catalog.domain.repository.CatalogCategoriaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CatalogCategoriaService {

    @Inject
    CatalogCategoriaRepository repository;

    public Uni<List<CatalogModel>> getAllCategorias() {
        return repository.findAll();
    }

    public Uni<CatalogModel> getCategoriaById(String id) {
        return repository.findById(id)
                .onItem().transform(opt -> opt.orElseThrow(
                        () -> new ResourceNotFoundException("Categoría no encontrada con id: " + id)
                ));
    }

    public Uni<CatalogModel> createCategoria(CatalogModel categoria) {
        categoria.setId(UUID.randomUUID().toString());
        categoria.setFechaCreacion(LocalDateTime.now());
        categoria.setEstado(1); // Activo por defecto
        return repository.save(categoria);
    }

    public Uni<CatalogModel> updateCategoria(String id, CatalogModel categoria) {
        return getCategoriaById(id)
                .onItem().transformToUni(existing -> {
                    categoria.setId(existing.getId());
                    categoria.setFechaCreacion(existing.getFechaCreacion());
                    return repository.update(categoria);
                });
    }

    public Uni<Void> deleteCategoria(String id) {
        return repository.deleteById(id)
                .onItem().transform(deleted -> {
                    if (!deleted) {
                        throw new ResourceNotFoundException("Categoría no encontrada con id: " + id);
                    }
                    return null;
                });
    }

    public Uni<List<CatalogModel>> getCategoriasActivas() {
        return repository.findByEstado(1);
    }
}