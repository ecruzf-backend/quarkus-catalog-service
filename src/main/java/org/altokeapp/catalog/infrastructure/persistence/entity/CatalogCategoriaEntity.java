package org.altokeapp.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "catalogo_categoria")
public class CatalogCategoriaEntity {
    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "icono", length = 250, nullable = false)
    private String icono;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "estado", nullable = false, columnDefinition = "TINYINT(1 DEFAULT 1)")
    private boolean estado;
}
