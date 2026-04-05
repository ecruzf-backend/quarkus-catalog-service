package org.altokeapp.catalog.infrastructure.adapters.persistence.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.altokeapp.catalog.infrastructure.adapters.persistence.entity.CatalogCategoryEntity;

@ApplicationScoped
public class CatalogCategoryPanacheRepository implements PanacheRepositoryBase<CatalogCategoryEntity, String> {

    public Uni<CatalogCategoryEntity> findByName(
            String name) {
        return find("name", name).firstResult();
    }

    public Uni<Boolean> existsByName(String name) {
        return find("name", name)
                .count()
                .map(count -> count > 0);
    }
}
