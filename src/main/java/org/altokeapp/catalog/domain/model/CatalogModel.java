package org.altokeapp.catalog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogModel {
    private String id;
    private String nombres;
    private String icono;
    private Integer estado;
    private LocalDateTime fechaCreacion;

}
