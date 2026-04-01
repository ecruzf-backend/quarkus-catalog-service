package org.altokeapp.catalog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogCategoriaResponse {
    private String id;
    private String nombres;
    private String icono;
    private LocalDateTime fechaCreacion;
    private Integer estado;
}
