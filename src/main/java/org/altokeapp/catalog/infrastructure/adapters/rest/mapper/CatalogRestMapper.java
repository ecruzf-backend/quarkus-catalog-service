package org.altokeapp.catalog.infrastructure.adapters.rest.mapper;

import org.altokeapp.catalog.domain.model.CatalogModel;
import org.altokeapp.catalog.infrastructure.rest.data.CatalogRequest;
import org.altokeapp.catalog.infrastructure.rest.data.CatalogResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jakarta-cdi")
public interface CatalogRestMapper {
    CatalogModel toDomain(CatalogRequest request);

    CatalogResponse toResponse(CatalogModel model);

    List<CatalogResponse> toResponseList(
            List<CatalogModel> models);
}
