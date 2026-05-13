package com.upc.careme.controllers;

import com.upc.careme.dtos.TareaServicioDTO;
import com.upc.careme.services.TareaServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas-servicio")
public class TareaServicioController {

    @Autowired
    private TareaServicioService tareaServicioService;

    @PostMapping
    public TareaServicioDTO registrar(@RequestBody TareaServicioDTO dto) {
        return tareaServicioService.insertar(dto);
    }

    @GetMapping
    public List<TareaServicioDTO> listar() {
        return tareaServicioService.listar();
    }
}