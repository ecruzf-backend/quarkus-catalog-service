package org.altokeapp.catalog.infrastructure.adapters.persistence.adapter;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.altokeapp.catalog.application.port.out.CatalogOutputPort;
import org.altokeapp.catalog.domain.model.CatalogModel;
import org.altokeapp.catalog.infrastructure.adapters.persistence.entity.CatalogCategoryEntity;
import org.altokeapp.catalog.infrastructure.adapters.persistence.mapper.CatalogCategoryMapper;
import org.altokeapp.catalog.infrastructure.adapters.persistence.repository.CatalogCategoryPanacheRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class CatalogCategoryRepositoryAdapter implements CatalogOutputPort {

    private final CatalogCategoryPanacheRepository catalogCategoryRepository;
    private final CatalogCategoryMapper catalogCategoryMapper;
    @Override
    @WithSession
    public Uni<CatalogModel> createCategory(CatalogModel catalogModel) {
        CatalogCategoryEntity entity = catalogCategoryMapper.toEntity(catalogModel);
        return catalogCategoryRepository
                .persist(entity)
                .map(catalogCategoryMapper::toDomain);
    }

    @Override
    public Uni<Optional<CatalogModel>> getCategoryById(String id) {
        return catalogCategoryRepository
                .findById(id)
                .map(entity -> Optional.ofNullable(entity)
                        .map(catalogCategoryMapper::toDomain));
    }

    @Override
    public Uni<List<CatalogModel>> getAllCategories() {
        return catalogCategoryRepository
                .findAll()
                .list()
                .map(entities -> entities.stream()
                        .map(catalogCategoryMapper::toDomain)
                        .toList());
    }

    @Override
    public Uni<CatalogModel> updateCategory(String id, CatalogModel catalogModel) {
        CatalogCategoryEntity entity = catalogCategoryMapper.toEntity(catalogModel);
        entity.setId(id);
            return catalogCategoryRepository.persist(entity)
                .map(catalogCategoryMapper::toDomain);
    }

    @Override
    public Uni<Void> deleteCategory(String id) {
        return catalogCategoryRepository.deleteById(id)
                .replaceWithVoid();
    }

    @Override
    public Uni<Boolean> existsByName(String name) {
        return catalogCategoryRepository.existsByName(name);
    }

    @Override
    public Uni<Optional<CatalogModel>> findByName(String name) {
        return catalogCategoryRepository.findByName(name)
                .map(entity -> Optional.ofNullable(entity)
                        .map(catalogCategoryMapper::toDomain));
    }
}
