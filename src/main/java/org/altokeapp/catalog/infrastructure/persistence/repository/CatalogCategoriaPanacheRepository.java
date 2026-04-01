package org.altokeapp.catalog.infrastructure.persistence.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.altokeapp.catalog.infrastructure.persistence.entity.CatalogCategoriaEntity;

import java.util.List;

@ApplicationScoped
public class CatalogCategoriaPanacheRepository implements PanacheRepositoryBase<CatalogCategoriaEntity, String> {

    /**
     * Busca todas las categorías por estado
     */

    public Uni<List<CatalogCategoriaEntity>> findByEstado(Integer estado) {
        return find("estado", estado).list();
    }

    /**
     * Busca categorías por documento de identidad
     */
    public Uni<List<CatalogCategoriaEntity>> findByDocumento(String documentoIdentidad) {
        return find("documentoIdentidad", documentoIdentidad).list();
    }

    /**
     * Busca categorías activas (estado = 1)
     */
    public Uni<List<CatalogCategoriaEntity>> findActivas() {
        return find("estado", 1).list();
    }

    /**
     * Busca por región
     */
    public Uni<List<CatalogCategoriaEntity>> findByRegion(String regionId) {
        return find("regionId", regionId).list();
    }

    /**
     * Busca por provincia
     */
    public Uni<List<CatalogCategoriaEntity>> findByProvincia(String provinciaId) {
        return find("provinciaId", provinciaId).list();
    }
}
