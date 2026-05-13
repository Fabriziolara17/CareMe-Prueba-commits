package com.upc.careme.controllers;

import com.upc.careme.dtos.PacienteDTO;
import com.upc.careme.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public PacienteDTO registrar(@RequestBody PacienteDTO dto) {
        return pacienteService.insertar(dto);
    }

    @GetMapping
    public List<PacienteDTO> listar() {
        return pacienteService.listar();
    }
}