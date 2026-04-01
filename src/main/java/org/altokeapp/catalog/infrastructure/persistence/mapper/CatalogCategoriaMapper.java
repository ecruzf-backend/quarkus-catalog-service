package org.altokeapp.catalog.infrastructure.persistence.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.altokeapp.catalog.domain.model.CatalogModel;
import org.altokeapp.catalog.infrastructure.persistence.entity.CatalogCategoriaEntity;

@ApplicationScoped
public class CatalogCategoriaMapper {
    public CatalogModel toDomain(CatalogCategoriaEntity entity) {
        if (entity == null) {
            return null;
        }

        CatalogModel domain = new CatalogModel();
        domain.setId(entity.getId());
        domain.setNombres(entity.getNombre());
        domain.setIcono(entity.getIcono());
        domain.setFechaCreacion(entity.getFechaCreacion());
        domain.setEstado(entity.getEstado());

        return domain;
    }

    public CatalogCategoriaEntity toEntity(CatalogModel domain) {
        if (domain == null) {
            return null;
        }

        CatalogCategoriaEntity entity = new CatalogCategoriaEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombres());
        entity.setIcono(domain.getIcono());
        entity.setFechaCreacion(domain.getFechaCreacion());
        entity.setEstado(domain.getEstado());

        return entity;
    }

}
