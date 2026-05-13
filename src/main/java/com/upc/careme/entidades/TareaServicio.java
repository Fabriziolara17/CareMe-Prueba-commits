package com.upc.careme.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tareas_servicio")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TareaServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTarea;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @Column(length = 255, nullable = false)
    private String descripcion;
}