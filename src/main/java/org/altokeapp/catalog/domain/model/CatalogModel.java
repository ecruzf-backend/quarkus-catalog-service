package org.altokeapp.catalog.domain.model;

import java.time.LocalDateTime;

public record CatalogModel(
        String id,
        String name,
        String icon,
        boolean active,
        LocalDateTime createdAt
        ) {
}
