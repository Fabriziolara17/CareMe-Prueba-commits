package com.upc.careme.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TareaServicioDTO {
    private Integer idTarea;
    private ServicioDTO servicio;
    private String descripcion;
}