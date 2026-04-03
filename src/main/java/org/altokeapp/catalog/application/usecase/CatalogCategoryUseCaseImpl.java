package org.altokeapp.catalog.application.usecase;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.altokeapp.catalog.application.port.in.CatalogInputPort;
import org.altokeapp.catalog.application.port.out.CatalogOutputPort;
import org.altokeapp.catalog.domain.exception.BusinessException;
import org.altokeapp.catalog.domain.exception.ResourceNotFoundException;
import org.altokeapp.catalog.domain.model.CatalogModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class CatalogCategoryUseCaseImpl implements CatalogInputPort {

    private final CatalogOutputPort catalogOutputPort;
    @Override
    public Uni<CatalogModel> createCategory(CatalogModel catalogModel) {
        Log.infof("Creando categoría con nombre: %s", catalogModel.name());
        CatalogModel modelWithId = new CatalogModel(
                UUID.randomUUID().toString(),
                catalogModel.name(),
                catalogModel.icon(),
                catalogModel.active(),
                LocalDateTime.now()
        );
        return validateUniqueName(modelWithId.name())
                .onItem().transformToUni(voidItem ->
                        catalogOutputPort.createCategory(modelWithId)
                );
    }

    @Override
    public Uni<CatalogModel> getCategoryById(String id) {
        return catalogOutputPort.getCategoryById(id)
                .onItem().transform(opt -> opt.orElseThrow(
                        () -> new ResourceNotFoundException("Categoría no encontrada con id: " + id)
                ));
    }

    @Override
    public Uni<List<CatalogModel>> getAllCategories() {
        return catalogOutputPort.getAllCategories();
    }

    @Override
    public Uni<CatalogModel> updateCategory(String id, CatalogModel catalogModel) {
        return catalogOutputPort.getCategoryById(id)
                .onItem().transformToUni(opt -> {
                    CatalogModel existing = opt.orElseThrow(
                            () -> new ResourceNotFoundException(
                            "Categoría no encontrada con id: " + id)
                    );
                    return validateUniqueNameForUpdate(id, catalogModel.name())
                            .map(voidItem -> existing);
                })
                .onItem().transformToUni(existing -> {
                    CatalogModel updated = new CatalogModel(
                            id,
                            catalogModel.name(),
                            catalogModel.icon(),
                            catalogModel.active(),
                            existing.createdAt()
                    );
                    return catalogOutputPort.updateCategory(id, updated);
                });
    }

    @Override
    public Uni<Void> deleteCategory(String id) {
        return catalogOutputPort.getCategoryById(id)
                .onItem().transformToUni(opt -> {
                    opt.orElseThrow(() -> new ResourceNotFoundException(
                            "Categoría no encontrada con id: " + id)
                    );
                    return catalogOutputPort.deleteCategory(id);
                });
    }

    private Uni<Void> validateUniqueName(String name) {
        return catalogOutputPort.existsByName(name)
                .onItem().transformToUni(exists -> exists
                        ? Uni.createFrom().failure(
                        new BusinessException("Ya existe una categoría con nombre: " + name))
                        : Uni.createFrom().voidItem()
                );
    }

    private Uni<Void> validateUniqueNameForUpdate(String id, String name) {
        return catalogOutputPort.findByName(name)
                .onItem().transformToUni(opt -> {
                    boolean existeEnOtroRegistro = opt.isPresent()
                            && !opt.get().id().equals(id);
                    return existeEnOtroRegistro
                            ? Uni.createFrom().failure(
                            new BusinessException("Ya existe una categoría con nombre: " + name))
                            : Uni.createFrom().voidItem();
                });
    }
}