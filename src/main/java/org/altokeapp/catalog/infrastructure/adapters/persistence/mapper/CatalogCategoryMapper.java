package org.altokeapp.catalog.infrastructure.adapters.persistence.mapper;

import org.altokeapp.catalog.domain.model.CatalogModel;
import org.altokeapp.catalog.infrastructure.adapters.persistence.entity.CatalogCategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta-cdi")
public interface CatalogCategoryMapper {
    CatalogModel toDomain(CatalogCategoryEntity entity);

    CatalogCategoryEntity toEntity(CatalogModel domain);

}
