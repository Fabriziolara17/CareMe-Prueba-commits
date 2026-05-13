package com.upc.careme.controllers;

import com.upc.careme.dtos.CalificacionDTO;
import com.upc.careme.dtos.CalificacionRequestDTO;
import com.upc.careme.services.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @PostMapping
    public CalificacionDTO registrar(@RequestBody CalificacionDTO dto) {
        return calificacionService.insertar(dto);
    }

    @GetMapping
    public List<CalificacionDTO> listar() {
        return calificacionService.listar();
    }

    @PostMapping("/resena")
    public String registrarResena(@RequestBody CalificacionRequestDTO request) {
        return calificacionService.registrarResena(request);
    }
}