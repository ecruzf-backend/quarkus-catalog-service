package org.altokeapp.catalog.infrastructure.persistence.adapter;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.altokeapp.catalog.domain.model.CatalogModel;
import org.altokeapp.catalog.domain.repository.CatalogCategoriaRepository;
import org.altokeapp.catalog.infrastructure.persistence.entity.CatalogCategoriaEntity;
import org.altokeapp.catalog.infrastructure.persistence.mapper.CatalogCategoriaMapper;
import org.altokeapp.catalog.infrastructure.persistence.repository.CatalogCategoriaPanacheRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class CatalogCategoriaRepositoryAdapter implements CatalogCategoriaRepository {
    @Inject
    CatalogCategoriaPanacheRepository panacheRepository;
    @Inject
    CatalogCategoriaMapper mapper;
    @Override
    @WithSession
    public Uni<List<CatalogModel>> findAll() {
        return panacheRepository.listAll()
                .onItem().transform(entities -> entities.stream()
                        .map(mapper::toDomain)
                        .collect(Collectors.toList()));
    }
    @Override
    @WithSession
    public Uni<Optional<CatalogModel>> findById(String id) {
        return panacheRepository.findById(id)
                .onItem().transform(entity ->
                        entity == null
                                ? Optional.empty()
                                : Optional.of(mapper.toDomain(entity))
                );
    }

    @Override
    @Transactional
    public Uni<CatalogModel> save(CatalogModel catalogModel) {
        CatalogCategoriaEntity entity = mapper.toEntity(catalogModel);
        return panacheRepository.persist(entity)
                .onItem().transform(mapper::toDomain);
    }
    @Override
    @WithSession
    public Uni<CatalogModel> update(CatalogModel catalogModel) {
        CatalogCategoriaEntity entity = mapper.toEntity(catalogModel);

        return panacheRepository.findById(entity.getId())
                .onItem().ifNotNull().transformToUni(existing -> {
                    // Actualizar campos
                    existing.setNombre(entity.getNombre());
                    existing.setIcono(entity.getIcono());
                    existing.setEstado(entity.getEstado());

                    return panacheRepository.persist(existing)
                            .onItem().transform(mapper::toDomain);
                })
                .onItem().ifNull().continueWith(() -> null);
    }
    @Override
    @WithSession
    public Uni<Boolean> deleteById(String id) {
        return panacheRepository.deleteById(id);
    }
    @Override
    @WithSession
    public Uni<List<CatalogModel>> findByEstado(Integer estado) {
        return panacheRepository.findByEstado(estado)
                .onItem().transform(entities -> entities.stream()
                        .map(mapper::toDomain)
                        .collect(Collectors.toList()));
    }
}
