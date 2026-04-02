package org.altokeapp.catalog.application.usecase;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.altokeapp.catalog.application.port.in.CatalogInputPort;
import org.altokeapp.catalog.application.port.out.CatalogOutputPort;
import org.altokeapp.catalog.domain.exception.BusinessException;
import org.altokeapp.catalog.domain.exception.ResourceNotFoundException;
import org.altokeapp.catalog.domain.model.CatalogModel;

@ApplicationScoped
@RequiredArgsConstructor
public class CatalogCategoriaService implements CatalogInputPort {

    private final CatalogOutputPort catalogOutputPort;
    @Override
    public Uni<CatalogModel> createCategoria(CatalogModel catalogModel) {
        Log.infof("Creando categoría con nombre: %s", catalogModel.nombre());
        return validarNombreUnico(catalogModel.nombre())
                .onItem().transformToUni(voidItem ->
                        catalogOutputPort.createCategoria(catalogModel)
                );
    }

    @Override
    public Uni<CatalogModel> getCategoriaById(String id) {
        return catalogOutputPort.getCategoriaById(id)
                .onItem().transform(opt -> opt.orElseThrow(
                        () -> new ResourceNotFoundException("Categoría no encontrada con id: " + id)
                ));
    }

    @Override
    public Multi<CatalogModel> getAllCategorias() {
        return catalogOutputPort.getAllCategorias();
    }

    @Override
    public Uni<CatalogModel> updateCategoria(String id, CatalogModel catalogModel) {
        return catalogOutputPort.getCategoriaById(id)
                .onItem().transformToUni(opt -> {
                    CatalogModel existing = opt.orElseThrow(
                            () -> new ResourceNotFoundException(
                            "Categoría no encontrada con id: " + id)
                    );
                    return validarNombreUnicoParaUpdate(id, catalogModel.nombre())
                            .map(voidItem -> existing);
                })
                .onItem().transformToUni(existing -> {
                    CatalogModel updated = new CatalogModel(
                            id,
                            catalogModel.nombre(),
                            catalogModel.icono(),
                            catalogModel.estado(),
                            existing.fechaCreacion()
                    );
                    return catalogOutputPort.updateCategoria(id, updated);
                });
    }

    @Override
    public Uni<Void> deleteCategoria(String id) {
        return catalogOutputPort.getCategoriaById(id)
                .onItem().transformToUni(opt -> {
                    opt.orElseThrow(() -> new ResourceNotFoundException(
                            "Categoría no encontrada con id: " + id)
                    );
                    return catalogOutputPort.deleteCategoria(id);
                });
    }

    private Uni<Void> validarNombreUnico(String nombre) {
        return catalogOutputPort.existsByNombre(nombre)
                .onItem().transformToUni(exists -> exists
                        ? Uni.createFrom().failure(
                        new BusinessException("Ya existe una categoría con nombre: " + nombre))
                        : Uni.createFrom().voidItem()
                );
    }

    private Uni<Void> validarNombreUnicoParaUpdate(String id, String nombre) {
        return catalogOutputPort.findByNombre(nombre)
                .onItem().transformToUni(opt -> {
                    boolean existeEnOtroRegistro = opt.isPresent()
                            && !opt.get().id().equals(id);
                    return existeEnOtroRegistro
                            ? Uni.createFrom().failure(
                            new BusinessException("Ya existe una categoría con nombre: " + nombre))
                            : Uni.createFrom().voidItem();
                });
    }
}