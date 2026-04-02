package org.altokeapp.catalog.domain.model;

import java.time.LocalDateTime;

public record CatalogModel(
        String id,
        String nombre,
        String icono,
        boolean estado,
        LocalDateTime fechaCreacion
        ) {
}
