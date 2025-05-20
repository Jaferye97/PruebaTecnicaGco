package com.yeimer.ecommerceapi.infra.adapters.out.db.entities;

import com.yeimer.ecommerceapi.domain.enums.TypeMovement;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Movimiento")
public class MovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idProducto", referencedColumnName = "id")
    private ProductEntity product;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 10)
    private TypeMovement type;

    @Column(name = "cantidad", nullable = false)
    private Integer amount;

    @Column(name = "fecha", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String description;
}
